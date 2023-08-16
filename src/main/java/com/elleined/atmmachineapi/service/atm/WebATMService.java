package com.elleined.atmmachineapi.service.atm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@Primary
public class WebATMService implements ATMService {
    @Override
    public void deposit(int userId, @NonNull BigDecimal amount) {

    }

    @Override
    public void withdraw(int userId, @NonNull BigDecimal amount) {

    }

    @Override
    public void peerToPeer(int senderId, @NonNull BigDecimal amount, int recipientId) {

    }
}
