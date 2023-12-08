package com.elleined.atmmachineapi.service.atm;

import com.elleined.atmmachineapi.exception.NotValidAmountException;
import com.elleined.atmmachineapi.exception.amount.ATMMinimumAmountException;
import com.elleined.atmmachineapi.exception.amount.ATMMaximumAmountException;
import com.elleined.atmmachineapi.exception.limit.LimitException;
import com.elleined.atmmachineapi.exception.limit.LimitExceptionPerDayException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.atm.transaction.DepositTransactionService;
import com.elleined.atmmachineapi.service.atm.transaction.TransactionService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.validator.ATMLimitPerDayValidator;
import com.elleined.atmmachineapi.service.validator.ATMLimitValidator;
import com.elleined.atmmachineapi.service.validator.ATMValidator;
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
public class DepositService implements ATMLimitValidator, ATMLimitPerDayValidator {
    public static final int MAXIMUM_DEPOSIT_AMOUNT = 10_000;
    public static final int DEPOSIT_LIMIT_PER_DAY = 10_000;
    public static final int MINIMUM_DEPOSIT_AMOUNT = 500;

    private final UserRepository userRepository;

    private final ATMValidator atmValidator;
    private final DepositTransactionService depositTransactionService;
    private final FeeService feeService;

    private final AppWalletService appWalletService;


    public DepositTransaction deposit(User currentUser, @NonNull BigDecimal depositedAmount)
            throws NotValidAmountException, LimitException {

        if (atmValidator.isNotValidAmount(depositedAmount)) throw new NotValidAmountException("Amount should be positive and cannot be zero!");
        if (isBelowMinimum(depositedAmount)) throw new ATMMaximumAmountException("Cannot deposit! because you are trying to deposit an amount that is below minimum which is " + MINIMUM_DEPOSIT_AMOUNT);
        if (isAboveMaximum(depositedAmount)) throw new ATMMinimumAmountException("You cannot deposit an amount that is greater than to deposit limit which is " + DEPOSIT_LIMIT_PER_DAY);
        if (reachedLimitAmountPerDay(currentUser)) throw new LimitExceptionPerDayException("Cannot deposit! Because you already reached the deposit limit per day which is " + DEPOSIT_LIMIT_PER_DAY);

        BigDecimal oldBalance = currentUser.getBalance();
        float depositFee = feeService.getDepositFee(depositedAmount);
        BigDecimal finalDepositedAmount = feeService.deductDepositFee(depositedAmount, depositFee);
        currentUser.setBalance(oldBalance.add(finalDepositedAmount));
        userRepository.save(currentUser);
        appWalletService.addAndSaveBalance(depositFee);

        DepositTransaction depositTransaction = depositTransactionService.save(currentUser, depositedAmount);
        log.debug("User with id of {} deposited amounting {} from {} because of deposit fee of {} which is the {}% of the deposited amount and now has new balance of {} from {}", currentUser.getId(), finalDepositedAmount, depositedAmount, depositFee, FeeService.DEPOSIT_FEE_PERCENTAGE, currentUser.getBalance(), oldBalance);
        return depositTransaction;
    }

    @Override
    public boolean reachedLimitAmountPerDay(User currentUser) {
        final LocalDateTime currentDateTimeMidnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        final LocalDateTime tomorrowMidnight = currentDateTimeMidnight.plusDays(1);
        List<DepositTransaction> userDepositTransactions = currentUser.getDepositTransactions();
        List<DepositTransaction> depositTransactions =
                TransactionService.getTransactionsByDateRange(userDepositTransactions, currentDateTimeMidnight, tomorrowMidnight);

        BigDecimal totalDepositAmount = depositTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElseGet(() -> new BigDecimal(0));
        int comparisonResult = totalDepositAmount.compareTo(new BigDecimal(DEPOSIT_LIMIT_PER_DAY));
        return comparisonResult >= 0;
    }

    @Override
    public boolean isBelowMinimum(BigDecimal depositedAmount) {
        return depositedAmount.compareTo(new BigDecimal(MINIMUM_DEPOSIT_AMOUNT)) < 0;
    }

    @Override
    public boolean isAboveMaximum(BigDecimal depositedAmount) {
        return depositedAmount.compareTo(new BigDecimal(MAXIMUM_DEPOSIT_AMOUNT)) > 0;
    }

}
