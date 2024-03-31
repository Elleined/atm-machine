package com.elleined.atmmachineapi.service.machine.deposit;

import com.elleined.atmmachineapi.exception.amount.NotValidAmountException;
import com.elleined.atmmachineapi.exception.limit.LimitException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public interface DepositService {
    DepositTransaction deposit(User currentUser, @NonNull BigDecimal depositedAmount)
            throws NotValidAmountException, LimitException;
}
