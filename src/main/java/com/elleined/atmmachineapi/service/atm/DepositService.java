package com.elleined.atmmachineapi.service.atm;

import com.elleined.atmmachineapi.exception.NotValidAmountException;
import com.elleined.atmmachineapi.exception.limit.DepositLimitPerDayException;
import com.elleined.atmmachineapi.exception.limit.LimitException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.atm.transaction.TransactionService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.utils.TransactionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DepositService {
    int DEPOSIT_LIMIT_PER_DAY = 10_000;

    private final UserRepository userRepository;

    private final ATMValidator atmValidator;

    private final TransactionService transactionService;

    private final FeeService feeService;

    private final AppWalletService appWalletService;


    public DepositTransaction deposit(User currentUser, @NonNull BigDecimal depositedAmount)
            throws NotValidAmountException, LimitException {

        if (atmValidator.isValidAmount(depositedAmount)) throw new NotValidAmountException("Amount should be positive and cannot be zero!");
        if (isDepositAmountAboveLimit(depositedAmount)) throw new LimitException("You cannot deposit an amount that is greater than to deposit limit which is " + DEPOSIT_LIMIT_PER_DAY);
        if (isUserReachedDepositLimitPerDay(currentUser)) throw new DepositLimitPerDayException("Cannot deposit! Because you already reached the deposit limit per day which is " + DEPOSIT_LIMIT_PER_DAY);

        BigDecimal oldBalance = currentUser.getBalance();
        float depositFee = feeService.getDepositFee(depositedAmount);
        BigDecimal finalDepositedAmount = feeService.deductDepositFee(depositedAmount, depositFee);
        currentUser.setBalance(oldBalance.add(finalDepositedAmount));
        userRepository.save(currentUser);
        appWalletService.addAndSaveBalance(depositFee);

        DepositTransaction depositTransaction = saveDepositTransaction(currentUser, depositedAmount);
        log.debug("User with id of {} deposited amounting {} from {} because of deposit fee of {} which is the {}% of the deposited amount and now has new balance of {} from {}", currentUser.getId(), finalDepositedAmount, depositedAmount, depositFee, FeeService.DEPOSIT_FEE_PERCENTAGE, currentUser.getBalance(), oldBalance);
        return depositTransaction;
    }

    public boolean isDepositAmountAboveLimit(BigDecimal depositedAmount) {
        return depositedAmount.compareTo(new BigDecimal(DEPOSIT_LIMIT_PER_DAY)) > 0;
    }

    private DepositTransaction saveDepositTransaction(User user, @NonNull BigDecimal depositedAmount) {
        String trn = UUID.randomUUID().toString();

        DepositTransaction depositTransaction = DepositTransaction.builder()
                .trn(trn)
                .amount(depositedAmount)
                .transactionDate(LocalDateTime.now())
                .user(user)
                .build();

        transactionService.save(depositTransaction);
        log.debug("Deposit transaction saved with trn of {}", trn);
        return depositTransaction;
    }

    public boolean isUserReachedDepositLimitPerDay(User currentUser) {
        final LocalDateTime currentDateTimeMidnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        final LocalDateTime tomorrowMidnight = currentDateTimeMidnight.plusDays(1);
        List<DepositTransaction> userDepositTransactions = currentUser.getDepositTransactions();
        List<DepositTransaction>  depositTransactions =
                TransactionUtils.getTransactionsByDateRange(userDepositTransactions, currentDateTimeMidnight, tomorrowMidnight);

        BigDecimal totalDepositAmount = depositTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElseGet(() -> new BigDecimal(0));
        int comparisonResult = totalDepositAmount.compareTo(new BigDecimal(DEPOSIT_LIMIT_PER_DAY));
        return comparisonResult >= 0;
    }
}
