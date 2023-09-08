package com.elleined.atmmachineapi.service.atm.transaction;

import com.elleined.atmmachineapi.model.transaction.Transaction;

public interface TransactionService {

    Transaction save(Transaction t);
    Transaction getById(int transactionId);
    Transaction getByTRN(String trn);
}
