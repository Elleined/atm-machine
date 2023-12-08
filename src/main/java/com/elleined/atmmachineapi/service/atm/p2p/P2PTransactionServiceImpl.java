package com.elleined.atmmachineapi.service.atm.p2p;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.repository.transaction.PeerToPeerTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class P2PTransactionServiceImpl implements P2PTransactionService {
    private final PeerToPeerTransactionRepository peerToPeerTransactionRepository;

    @Override
    public PeerToPeerTransaction save(User sender, User receiver, BigDecimal sentAmount) {
        String trn = UUID.randomUUID().toString();

        PeerToPeerTransaction peerToPeerTransaction = PeerToPeerTransaction.builder()
                .trn(trn)
                .amount(sentAmount)
                .transactionDate(LocalDateTime.now())
                .sender(sender)
                .receiver(receiver)
                .build();

        peerToPeerTransactionRepository.save(peerToPeerTransaction);
        log.debug("Peer to peer transaction saved successfully with trn of {}", trn);
        return peerToPeerTransaction;
    }

    @Override
    public List<PeerToPeerTransaction> getAllReceiveMoneyTransactions(User currentUser) {
        return currentUser.getReceiveMoneyTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .toList();
    }

    @Override
    public List<PeerToPeerTransaction> getAllSentMoneyTransactions(User currentUser) {
        return currentUser.getSentMoneyTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .toList();
    }
}
