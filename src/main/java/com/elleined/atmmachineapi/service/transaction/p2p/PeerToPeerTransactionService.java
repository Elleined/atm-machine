package com.elleined.atmmachineapi.service.transaction.p2p;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.service.transaction.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface PeerToPeerTransactionService extends TransactionService<PeerToPeerTransaction> {
    PeerToPeerTransaction save(User currentUser, User receiver, BigDecimal amount);
    Page<PeerToPeerTransaction> getAllReceiveMoneyTransactions(User currentUser, Pageable pageable);
    Page<PeerToPeerTransaction> getAllSentMoneyTransactions(User currentUser, Pageable pageable);
}
