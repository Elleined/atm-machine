package com.moneysender.Money.Sender.service;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.service.atm.ATMValidator;
import com.elleined.atmmachineapi.service.atm.PeerToPeerService;
import com.elleined.atmmachineapi.service.user.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PeerToPeerServiceTest {
    private final PeerToPeerService peerToPeerService;
    private final UserServiceImpl userServiceImpl;
    private final ATMValidator atmValidator;

    @Autowired
    public PeerToPeerServiceTest(PeerToPeerService peerToPeerService, UserServiceImpl userServiceImpl, ATMValidator atmValidator) {
        this.peerToPeerService = peerToPeerService;
        this.userServiceImpl = userServiceImpl;
        this.atmValidator = atmValidator;
    }

    @Test
    void amountShouldNotEqualToZeroAndShouldNotBeNegative() {
        BigDecimal amount = new BigDecimal(1);
        assertFalse(atmValidator.isValidAmount(amount), "Amount should not be zero");
    }

    @Test
    void isSenderHaveFunds() {
        User sender = userServiceImpl.getById(2);
        BigDecimal amount = new BigDecimal(100);
        assertFalse(atmValidator.isBalanceEnough(sender, amount), "Insufficient Funds");
    }
}
