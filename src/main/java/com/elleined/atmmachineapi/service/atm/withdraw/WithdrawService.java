package com.elleined.atmmachineapi.service.atm.withdraw;


import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.NotValidAmountException;
import com.elleined.atmmachineapi.exception.amount.ATMMaximumAmountException;
import com.elleined.atmmachineapi.exception.amount.ATMMinimumAmountException;
import com.elleined.atmmachineapi.exception.limit.LimitException;
import com.elleined.atmmachineapi.exception.limit.LimitExceptionPerDayException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.atm.TransactionService;
import com.elleined.atmmachineapi.service.atm.validator.ATMLimitPerDayValidator;
import com.elleined.atmmachineapi.service.atm.validator.ATMLimitValidator;
import com.elleined.atmmachineapi.service.atm.validator.ATMValidator;
import com.elleined.atmmachineapi.service.fee.FeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WithdrawService implements ATMLimitValidator, ATMLimitPerDayValidator {
    public static final int WITHDRAWAL_LIMIT_PER_DAY = 10_000;
    public static final int MAXIMUM_WITHDRAW_AMOUNT = 10_000;
    public static final int MINIMUM_WITHDRAW_AMOUNT = 500;

    private final UserRepository userRepository;

    private final ATMValidator atmValidator;

    private final WithdrawTransactionService withdrawTransactionService;

    private final FeeService feeService;
    private final AppWalletService appWalletService;


    public WithdrawTransaction withdraw(User currentUser, @NonNull BigDecimal withdrawalAmount)
            throws InsufficientFundException,
            NotValidAmountException,
            LimitException {

        if (atmValidator.isNotValidAmount(withdrawalAmount)) throw new NotValidAmountException("Amount should be positive and cannot be zero!");
        if (currentUser.isBalanceNotEnough(withdrawalAmount)) throw new InsufficientFundException("Insufficient Funds!");
        if (isBelowMinimum(withdrawalAmount)) throw new ATMMinimumAmountException("Cannot withdraw! because you are trying to withdraw an amount that below to minimum amount which is " + MINIMUM_WITHDRAW_AMOUNT);
        if (isAboveMaximum(withdrawalAmount)) throw new ATMMaximumAmountException("Cannot withdraw! You cannot withdraw an amount that is greater than withdraw limit which is " + MAXIMUM_WITHDRAW_AMOUNT);
        if (reachedLimitAmountPerDay(currentUser)) throw new LimitExceptionPerDayException("Cannot withdraw! You already reached withdrawal limit per day which is " + WITHDRAWAL_LIMIT_PER_DAY);

        BigDecimal oldBalance = currentUser.getBalance();
        float withdrawalFee = feeService.getWithdrawalFee(withdrawalAmount);
        BigDecimal finalWithdrawalAmount = withdrawalAmount.subtract(new BigDecimal(withdrawalFee));
        currentUser.setBalance(oldBalance.subtract(withdrawalAmount));
        userRepository.save(currentUser);
        appWalletService.addAndSaveBalance(withdrawalFee);

        WithdrawTransaction withdrawTransaction = withdrawTransactionService.save(currentUser, withdrawalAmount);
        log.debug("User with id of {} withdraw amounting {} from {} because of withdrawal fee of {} which is the {}% of withdrawn amount and has new balance of {} from {}", currentUser.getId(), finalWithdrawalAmount, withdrawalAmount, withdrawalFee, FeeService.WITHDRAWAL_FEE_PERCENTAGE, currentUser.getBalance(), oldBalance);
        return withdrawTransaction;
    }
    @Override
    public boolean isAboveMaximum(BigDecimal withdrawalAmount) {
        return withdrawalAmount.compareTo(new BigDecimal(MAXIMUM_WITHDRAW_AMOUNT)) > 0;
    }

    @Override
    public boolean isBelowMinimum(BigDecimal amountToBeWithdrawn) {
        return amountToBeWithdrawn.compareTo(new BigDecimal(MINIMUM_WITHDRAW_AMOUNT)) < 0;
    }

    @Override
    public boolean reachedLimitAmountPerDay(User currentUser) {
        final LocalDateTime currentDateTimeMidnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        final LocalDateTime tomorrowMidnight = currentDateTimeMidnight.plusDays(1);
        List<WithdrawTransaction> userWithdrawTransactions = currentUser.getWithdrawTransactions();
        List<WithdrawTransaction>  withdrawTransactions =
                TransactionService.getTransactionsByDateRange(userWithdrawTransactions, currentDateTimeMidnight, tomorrowMidnight);

        BigDecimal totalWithdrawAmount = withdrawTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElseGet(() -> new BigDecimal(0));
        int comparisonResult = totalWithdrawAmount.compareTo(new BigDecimal(WITHDRAWAL_LIMIT_PER_DAY));
        return comparisonResult >= 0;
    }
}
