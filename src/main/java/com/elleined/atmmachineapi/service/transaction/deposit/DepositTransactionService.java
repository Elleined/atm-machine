package com.elleined.atmmachineapi.service.transaction.deposit;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.service.transaction.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface DepositTransactionService extends TransactionService<DepositTransaction> {
    Page<DepositTransaction> getAll(User currentUser, Pageable pageable);
    DepositTransaction save(User currentUser, BigDecimal amount);
}
