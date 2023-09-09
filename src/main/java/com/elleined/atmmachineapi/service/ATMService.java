package com.elleined.atmmachineapi.service;

import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.NotValidAmountException;
import com.elleined.atmmachineapi.exception.SendingToHimselfException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public interface ATMService {
    DepositTransaction deposit(User currentUser, @NonNull BigDecimal depositedAmount)
            throws NotValidAmountException;

    WithdrawTransaction withdraw(User currentUser, @NonNull BigDecimal withdrawnAmount)
            throws InsufficientFundException,
            NotValidAmountException;

    PeerToPeerTransaction peerToPeer(User sender, User receiver, @NonNull BigDecimal sentAmount)
            throws SendingToHimselfException,
            InsufficientFundException,
            NotValidAmountException;
}
