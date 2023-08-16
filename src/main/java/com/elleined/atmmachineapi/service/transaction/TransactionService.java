package com.elleined.atmmachineapi.service.transaction;

import com.elleined.atmmachineapi.model.transaction.ATMTransaction;

import java.util.Optional;

public interface TransactionService {

    ATMTransaction save(ATMTransaction t);
    ATMTransaction getById(int transactionId);
    ATMTransaction getByTRN(String trn);
}
