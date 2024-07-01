package com.elleined.atmmachineapi.service.validator.deposit;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.repository.transaction.DepositTransactionRepository;
import com.elleined.atmmachineapi.service.validator.ATMLimitPerDayValidator;
import com.elleined.atmmachineapi.service.validator.ATMLimitValidator;
import com.elleined.atmmachineapi.service.validator.ATMValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Qualifier("depositValidator")
public class DepositValidator implements ATMLimitValidator, ATMLimitPerDayValidator, ATMValidator {
    private final DepositTransactionRepository depositTransactionRepository;
    public static final int MAXIMUM_DEPOSIT_AMOUNT = 10_000;
    public static final int DEPOSIT_LIMIT_PER_DAY = 10_000;
    public static final int MINIMUM_DEPOSIT_AMOUNT = 500;

    @Override
    public boolean reachedLimitAmountPerDay(User currentUser, BigDecimal amount) {
        final LocalDateTime currentDateTimeMidnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        final LocalDateTime tomorrowMidnight = currentDateTimeMidnight.plusDays(1);
        List<DepositTransaction> depositTransactionsByDateRange = depositTransactionRepository.findAllByDateRange(currentUser, currentDateTimeMidnight, tomorrowMidnight);

        BigDecimal totalDepositAmountByDateRange = depositTransactionsByDateRange.stream()
                .map(Transaction::getAmount)
                .reduce(new BigDecimal(0), BigDecimal::add);

        int comparisonResult = totalDepositAmountByDateRange.add(amount)
                .compareTo(new BigDecimal(DEPOSIT_LIMIT_PER_DAY));

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
