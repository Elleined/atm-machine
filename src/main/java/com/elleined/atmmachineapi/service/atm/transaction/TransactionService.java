package com.elleined.atmmachineapi.service.atm.transaction;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;

import java.util.List;

public interface TransactionService {

    Transaction save(Transaction transaction);

    List<WithdrawTransaction> getAllWithdrawalTransactions(User currentUser);
    List<DepositTransaction> getAllDepositTransactions(User currentUser);
    List<PeerToPeerTransaction> getAllReceiveMoneyTransactions(User currentUser);
    List<PeerToPeerTransaction> getAllSentMoneyTransactions(User currentUser);
}
