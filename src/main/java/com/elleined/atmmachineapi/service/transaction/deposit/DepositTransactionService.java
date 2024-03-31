package com.elleined.atmmachineapi.service.transaction.deposit;

import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.request.transaction.DepositTransactionRequest;
import com.elleined.atmmachineapi.service.transaction.TransactionService;

public interface DepositTransactionService extends TransactionService<DepositTransaction, DepositTransactionRequest> {
}
