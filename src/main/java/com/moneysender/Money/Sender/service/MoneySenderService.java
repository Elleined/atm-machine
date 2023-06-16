package com.moneysender.Money.Sender.service;

import com.moneysender.Money.Sender.exception.InsufficientAmountException;
import com.moneysender.Money.Sender.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class MoneySenderService {

    private final UserService userService;
    @Transactional
    public void sendMoney(int senderId, BigDecimal amountToBeSent, int recipientId) {
        User sender = userService.getById(senderId);
        if (isBalanceEnoughToSend(sender, amountToBeSent)) {
            log.trace("Sender balance is {} and trying to send {} which is not enough!", sender.getBalance(), amountToBeSent);
            throw new InsufficientAmountException("Balance not enough!");
        }
        User recipient = userService.getById(recipientId);
    }

    public boolean isBalanceEnoughToSend(User sender, BigDecimal amountToBeSent) {
        return sender.getBalance().compareTo(amountToBeSent) < 0;
    }
}
