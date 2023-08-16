package com.elleined.atmmachineapi.service;

import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.ATMTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerATMTransaction;
import com.elleined.atmmachineapi.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PeerToPeerService {
    private final UserService userService;
    private final ATMValidator atmValidator;
    private final TransactionService transactionService;

    public BigDecimal peerToPeer(int senderId, BigDecimal amount, int receiverId)
            throws IllegalArgumentException,
            InsufficientFundException,
            ResourceNotFoundException {

        User sender = userService.getById(senderId);
        if (atmValidator.isSenderSendingToHimself(senderId, receiverId)) {
            log.trace("User trying to send in recipient id of {} but turns out it is the same id of himself which is not allowed!", receiverId);
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

        User receiver = userService.getById(receiverId);
        updateSenderBalance(sender, amount);
        updateRecipientBalance(receiver, amount);
        savePeerToPeerTransaction(sender, amount, receiver);

        log.debug("Money send successfully to the receiver {} amounting {} your new balance is {}.", receiver.getName(), amount, sender.getBalance());
        return sender.getBalance();
    }

    private void updateSenderBalance(User sender, BigDecimal amountToBeDeducted) {
        BigDecimal newBalance = sender.getBalance().subtract(amountToBeDeducted);
        sender.setBalance(newBalance);
        userService.save(sender);
    }

    private void updateRecipientBalance(User receiver, BigDecimal amountToBeAdded) {
        BigDecimal newBalance = receiver.getBalance().add(amountToBeAdded);
        receiver.setBalance(newBalance);
        userService.save(receiver);
    }

    private void savePeerToPeerTransaction(User sender, BigDecimal amount, User receiver) {
        String trn = UUID.randomUUID().toString();

        ATMTransaction peerToPeerTransaction = PeerToPeerATMTransaction.builder()
                .trn(trn)
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .sender(sender)
                .receiver(receiver)
                .build();

        transactionService.save(peerToPeerTransaction);
        log.debug("Peer to peer transaction saved successfully with trn of {}", trn);
    }
}
