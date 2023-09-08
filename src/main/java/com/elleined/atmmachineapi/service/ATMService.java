package com.elleined.atmmachineapi.service;

import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public interface ATMService {
    DepositTransaction deposit(int userId, @NonNull BigDecimal amount);
    WithdrawTransaction withdraw(int userId, @NonNull BigDecimal amount);
    PeerToPeerTransaction peerToPeer(int senderId, @NonNull BigDecimal amount, int recipientId);
}
