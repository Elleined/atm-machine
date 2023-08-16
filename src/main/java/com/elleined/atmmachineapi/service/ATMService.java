package com.elleined.atmmachineapi.service;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public interface ATMService {
    void deposit(int userId, @NonNull BigDecimal amount);
    void withdraw(int userId, @NonNull BigDecimal amount);
    void peerToPeer(int senderId, @NonNull BigDecimal amount, int recipientId);
}
