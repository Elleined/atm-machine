package com.moneysender.Money.Sender.service;

import com.moneysender.Money.Sender.exception.InsufficientFundException;
import com.moneysender.Money.Sender.exception.ResourceNotFoundException;
import com.moneysender.Money.Sender.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MoneySenderService {

    private final UserService userService;
    private final TransactionService transactionService;
    private final ATMValidator atmValidator;

    @Transactional
    public BigDecimal sendMoney(int senderId, @NonNull BigDecimal amount, int recipientId)
            throws IllegalArgumentException,
            InsufficientFundException,
            ResourceNotFoundException {

        User sender = userService.getById(senderId);
        if (isSenderSendingToHimself(senderId, recipientId)) {
            log.trace("User trying to send in recipient id of {} but turns out it is the same id of himself which is not allowed!", recipientId);
            throw new IllegalArgumentException("You cannot send to yourself");
        }
        if (atmValidator.isValidAmount(amount)) {
            log.trace("Amount trying to send is {} which is less than 0 or a negative number", amount);
            throw new IllegalArgumentException("Amount should be positive and cannot be zero!");
        }
        if (atmValidator.isBalanceEnough(sender, amount)) {
            log.trace("Sender balance is {} and trying to send {} which is not enough!", sender.getBalance(), amount);
            throw new InsufficientFundException("Insufficient Funds!");
        }

        User recipient = userService.getById(recipientId);
        updateSenderBalance(sender, amount);
        updateRecipientBalance(recipient, amount);

        transactionService.save(amount, sender, recipient);

        log.debug("Money send successfully to the recipient {} amounting {} your new balance is {}.", recipient.getName(), amount, sender.getBalance());
        return sender.getBalance();
    }

    @Transactional
    private void updateSenderBalance(@NonNull User sender, @NonNull BigDecimal amountToBeDeducted) {
        BigDecimal newBalance = sender.getBalance().subtract(amountToBeDeducted);
        sender.setBalance(newBalance);
        userService.save(sender);
    }

    @Transactional
    private void updateRecipientBalance(@NonNull User recipient, @NonNull BigDecimal amountToBeAdded) {
        BigDecimal newBalance = recipient.getBalance().add(amountToBeAdded);
        recipient.setBalance(newBalance);
        userService.save(recipient);
    }

    public boolean isSenderSendingToHimself(int senderId, int recipientId) {
        return senderId == recipientId;
    }
}
