package com.elleined.atmmachineapi.service.transaction.withdraw;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.transaction.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface WithdrawTransactionService extends TransactionService<WithdrawTransaction> {
    WithdrawTransaction save(User currentUser, BigDecimal amount);
    Page<WithdrawTransaction> getAll(User currentUser, Pageable pageable);
}
