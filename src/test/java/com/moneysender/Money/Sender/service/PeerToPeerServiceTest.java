package com.moneysender.Money.Sender.service;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.service.ATMValidator;
import com.elleined.atmmachineapi.service.PeerToPeerService;
import com.elleined.atmmachineapi.service.UserService;
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
    private final UserService userService;
    private final ATMValidator atmValidator;

    @Autowired
    public PeerToPeerServiceTest(PeerToPeerService peerToPeerService, UserService userService, ATMValidator atmValidator) {
        this.peerToPeerService = peerToPeerService;
        this.userService = userService;
        this.atmValidator = atmValidator;
    }

    @Test
    void amountShouldNotEqualToZeroAndShouldNotBeNegative() {
        BigDecimal amount = new BigDecimal(1);
        assertFalse(atmValidator.isValidAmount(amount), "Amount should not be zero");
    }

    @Test
    void isSenderHaveFunds() {
        User sender = userService.getById(2);
        BigDecimal amount = new BigDecimal(100);
        assertFalse(atmValidator.isBalanceEnough(sender, amount), "Insufficient Funds");
    }
}
