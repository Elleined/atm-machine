package com.elleined.atmmachineapi.service.machine.deposit;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;

import java.math.BigDecimal;

public interface DepositService {
    DepositTransaction deposit(User currentUser, BigDecimal depositedAmount);
}
