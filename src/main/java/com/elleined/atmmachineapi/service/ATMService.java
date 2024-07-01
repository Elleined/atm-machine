package com.elleined.atmmachineapi.service;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;

import java.math.BigDecimal;

public interface ATMService {
    DepositTransaction deposit(User currentUser, BigDecimal depositedAmount);
    WithdrawTransaction withdraw(User currentUser, BigDecimal withdrawnAmount);
    PeerToPeerTransaction peerToPeer(User sender, User receiver, BigDecimal sentAmount);
}
