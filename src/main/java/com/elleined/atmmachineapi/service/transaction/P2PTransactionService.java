package com.elleined.atmmachineapi.service.transaction;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface P2PTransactionService {
    PeerToPeerTransaction save(User sender, User receiver, BigDecimal sentAmount);
    List<PeerToPeerTransaction> getAllReceiveMoneyTransactions(User currentUser);
    List<PeerToPeerTransaction> getAllSentMoneyTransactions(User currentUser);

}
