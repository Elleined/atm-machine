package com.moneysender.Money.Sender.service;

import com.moneysender.Money.Sender.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MoneySenderServiceTest {
    private final MoneySenderService moneySenderService;
    private final UserService userService;

    @Autowired
    public MoneySenderServiceTest(MoneySenderService moneySenderService, UserService userService) {
        this.moneySenderService = moneySenderService;
        this.userService = userService;
    }

    @Test
    void senderIdShouldNotBeAlsoTheRecipientsId() {
        int senderId = 1;
        int recipientId = 2;
        assertFalse(moneySenderService.isSenderSendingToHimself(senderId, recipientId), "sender id and recipient id should not be the same!");
    }

    @Test
    void amountShouldNotEqualToZeroAndShouldNotBeNegative() {
        BigDecimal amount = new BigDecimal(1);
        assertFalse(moneySenderService.isAmountLessThanZero(amount), "Amount should not be zero");
    }

    @Test
    void isSenderHaveFunds() {
        User sender = userService.getById(2);
        BigDecimal amount = new BigDecimal(100);
        assertFalse(moneySenderService.isBalanceEnoughToSend(sender, amount), "Insufficient Funds");
    }
}
