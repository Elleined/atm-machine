package com.elleined.atmmachineapi.service.transaction.withdraw;

import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.request.transaction.WithdrawTransactionRequest;
import com.elleined.atmmachineapi.service.transaction.TransactionService;

public interface WithdrawTransactionService extends TransactionService<WithdrawTransaction, WithdrawTransactionRequest> {
}
