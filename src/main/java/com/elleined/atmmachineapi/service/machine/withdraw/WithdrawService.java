package com.elleined.atmmachineapi.service.machine.withdraw;

import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.amount.NotValidAmountException;
import com.elleined.atmmachineapi.exception.limit.LimitException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public interface WithdrawService {
    WithdrawTransaction withdraw(User currentUser, @NonNull BigDecimal withdrawalAmount)
            throws InsufficientFundException,
            NotValidAmountException,
            LimitException;
}
