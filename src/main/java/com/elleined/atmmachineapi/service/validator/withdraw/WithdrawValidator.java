package com.elleined.atmmachineapi.service.validator.withdraw;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.transaction.TransactionService;
import com.elleined.atmmachineapi.service.validator.ATMLimitPerDayValidator;
import com.elleined.atmmachineapi.service.validator.ATMLimitValidator;
import com.elleined.atmmachineapi.service.validator.ATMValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Qualifier("withdrawValidator")
public class WithdrawValidator implements ATMLimitValidator, ATMLimitPerDayValidator, ATMValidator {

    public static final int WITHDRAWAL_LIMIT_PER_DAY = 10_000;
    public static final int MAXIMUM_WITHDRAW_AMOUNT = 10_000;
    public static final int MINIMUM_WITHDRAW_AMOUNT = 500;

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
