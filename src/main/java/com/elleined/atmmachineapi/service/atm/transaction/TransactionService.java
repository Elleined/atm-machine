package com.elleined.atmmachineapi.service.atm.transaction;

import com.elleined.atmmachineapi.model.transaction.ATMTransaction;

public interface TransactionService {

    ATMTransaction save(ATMTransaction t);
    ATMTransaction getById(int transactionId);
    ATMTransaction getByTRN(String trn);
}
