package com.elleined.atmmachineapi.service.validator.p2p;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.service.transaction.TransactionService;
import com.elleined.atmmachineapi.service.validator.ATMLimitPerDayValidator;
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
@Qualifier("peerToPeerValidator")
public class PeerToPeerValidator implements ATMLimitPerDayValidator, ATMValidator {
    public static final int PEER_TO_PEER_LIMIT_PER_DAY = 10_000;

    @Override
    public boolean reachedLimitAmountPerDay(User sender) {
        final LocalDateTime currentDateTimeMidnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        final LocalDateTime tomorrowMidnight = currentDateTimeMidnight.plusDays(1);
        List<PeerToPeerTransaction> userSentMoneyTransactions = sender.getSentMoneyTransactions();
        List<PeerToPeerTransaction>  sentMoneyTransactions =
                TransactionService.getTransactionsByDateRange(userSentMoneyTransactions, currentDateTimeMidnight, tomorrowMidnight);

        BigDecimal totalSentAmount = sentMoneyTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElseGet(() -> new BigDecimal(0));
        int comparisonResult = totalSentAmount.compareTo(new BigDecimal(PEER_TO_PEER_LIMIT_PER_DAY));
        return comparisonResult >= 0;
    }
}
