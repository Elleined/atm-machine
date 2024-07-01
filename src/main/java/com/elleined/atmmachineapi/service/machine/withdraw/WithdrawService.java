package com.elleined.atmmachineapi.service.machine.withdraw;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;

import java.math.BigDecimal;

public interface WithdrawService {
    WithdrawTransaction withdraw(User currentUser, BigDecimal withdrawalAmount);
}
