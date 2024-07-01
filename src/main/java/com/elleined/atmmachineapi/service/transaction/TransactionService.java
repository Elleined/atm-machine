package com.elleined.atmmachineapi.service.transaction;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.transaction.Transaction;

public interface TransactionService<T extends Transaction> {
    T getById(int id) throws ResourceNotFoundException;
}
