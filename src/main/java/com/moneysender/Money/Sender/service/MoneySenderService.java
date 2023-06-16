package com.moneysender.Money.Sender.service;

import com.moneysender.Money.Sender.exception.InsufficientFundException;
import com.moneysender.Money.Sender.model.User;
import com.moneysender.Money.Sender.repository.UserRepository;
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
    public void sendMoney(int senderId, BigDecimal amount, int recipientId) {
        User sender = userService.getById(senderId);
        if (isBalanceEnoughToSend(sender, amount)) {
            log.trace("Sender balance is {} and trying to send {} which is not enough!", sender.getBalance(), amount);
            throw new InsufficientFundException("Insufficient Funds!");
        }
        User recipient = userService.getById(recipientId);
        updateSenderBalance(sender, amount);
        updateRecipientBalance(recipient, amount);


        log.info("Money send successfully to the recipient {} amounting {} your new balance is {}.", recipient.getName(), amount, sender.getBalance());
    }

    @Transactional
    public void updateSenderBalance(User sender, BigDecimal amountToBeDeducted) {
        BigDecimal newBalance = sender.getBalance().subtract(amountToBeDeducted);
        sender.setBalance(newBalance);
        userService.save(sender);
    }

    @Transactional
    public void updateRecipientBalance(User recipient, BigDecimal amountToBeAdded) {
        BigDecimal newBalance = recipient.getBalance().add(amountToBeAdded);
        recipient.setBalance(newBalance);
        userService.save(recipient);
    }

    public boolean isBalanceEnoughToSend(User sender, BigDecimal amountToBeSent) {
        return sender.getBalance().compareTo(amountToBeSent) < 0;
    }
}
