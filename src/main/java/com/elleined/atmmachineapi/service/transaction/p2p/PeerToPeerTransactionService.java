package com.elleined.atmmachineapi.service.transaction.p2p;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.request.transaction.PeerToPeerTransactionRequest;
import com.elleined.atmmachineapi.service.transaction.TransactionService;

import java.util.List;

public interface PeerToPeerTransactionService extends TransactionService<PeerToPeerTransaction, PeerToPeerTransactionRequest> {
    List<PeerToPeerTransaction> getAllReceiveMoneyTransactions(User currentUser);
    List<PeerToPeerTransaction> getAllSentMoneyTransactions(User currentUser);
}
