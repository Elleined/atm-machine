package com.elleined.atmmachineapi.service.atm;

import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.service.atm.transaction.TransactionService;
import com.elleined.atmmachineapi.service.user.UserServiceImpl;
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
    private final UserServiceImpl userServiceImpl;
    private final ATMValidator atmValidator;
    private final TransactionService transactionService;

    public BigDecimal peerToPeer(int senderId, BigDecimal amount, int receiverId)
            throws IllegalArgumentException,
            InsufficientFundException,
            ResourceNotFoundException {

        User sender = userServiceImpl.getById(senderId);
        User receiver = userServiceImpl.getById(receiverId);

        if (atmValidator.isSenderSendingToHimself(senderId, receiverId)) throw new IllegalArgumentException("You cannot send to yourself");
        if (atmValidator.isValidAmount(amount)) throw new IllegalArgumentException("Amount should be positive and cannot be zero!");
        if (atmValidator.isBalanceEnough(sender, amount)) throw new InsufficientFundException("Insufficient Funds!");

        updateSenderBalance(sender, amount);
        updateRecipientBalance(receiver, amount);
        savePeerToPeerTransaction(sender, amount, receiver);

        log.debug("Money send successfully to the receiver {} amounting {} your new balance is {}.", receiver.getName(), amount, sender.getBalance());
        return sender.getBalance();
    }

    private void updateSenderBalance(User sender, BigDecimal amountToBeDeducted) {
        BigDecimal newBalance = sender.getBalance().subtract(amountToBeDeducted);
        sender.setBalance(newBalance);
        userServiceImpl.save(sender);
    }

    private void updateRecipientBalance(User receiver, BigDecimal amountToBeAdded) {
        BigDecimal newBalance = receiver.getBalance().add(amountToBeAdded);
        receiver.setBalance(newBalance);
        userServiceImpl.save(receiver);
    }

    private void savePeerToPeerTransaction(User sender, BigDecimal amount, User receiver) {
        String trn = UUID.randomUUID().toString();

        Transaction peerToPeerTransaction = PeerToPeerTransaction.builder()
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
