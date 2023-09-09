package com.elleined.atmmachineapi.service.atm;

import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.NotValidAmountException;
import com.elleined.atmmachineapi.exception.SendingToHimselfException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.service.atm.transaction.TransactionService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import lombok.NonNull;
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
    private final UserRepository userRepository;
    private final FeeService feeService;
    private final ATMValidator atmValidator;
    private final TransactionService transactionService;

    public PeerToPeerTransaction peerToPeer(User sender, User receiver, @NonNull BigDecimal sentAmount)
            throws SendingToHimselfException,
            InsufficientFundException,
            NotValidAmountException {

        if (atmValidator.isSenderSendingToHimself(sender, receiver)) throw new SendingToHimselfException("You cannot send to yourself");
        if (atmValidator.isValidAmount(sentAmount)) throw new NotValidAmountException("Amount should be positive and cannot be zero!");
        if (atmValidator.isBalanceEnough(sender, sentAmount)) throw new InsufficientFundException("Insufficient Funds!");

        updateSenderBalance(sender, sentAmount);
        updateRecipientBalance(receiver, sentAmount);
        feeService.deductP2pFee(sender, receiver, sentAmount);
        PeerToPeerTransaction peerToPeerTransaction = savePeerToPeerTransaction(sender, receiver, sentAmount);

        log.debug("Money send successfully to the receiver {} amounting {} your new balance is {}.", receiver.getName(), sentAmount, sender.getBalance());
        return peerToPeerTransaction;
    }

    private void updateSenderBalance(User sender, BigDecimal amountToBeDeducted) {
        BigDecimal newBalance = sender.getBalance().subtract(amountToBeDeducted);
        sender.setBalance(newBalance);
        userRepository.save(sender);
    }

    private void updateRecipientBalance(User receiver, BigDecimal amountToBeAdded) {
        BigDecimal newBalance = receiver.getBalance().add(amountToBeAdded);
        receiver.setBalance(newBalance);
        userRepository.save(receiver);
    }

    private PeerToPeerTransaction savePeerToPeerTransaction(User sender, User receiver, BigDecimal sentAmount) {
        String trn = UUID.randomUUID().toString();

        float p2pFee = feeService.getP2pFee(sentAmount);
        BigDecimal finalSentAmount = sentAmount.subtract(new BigDecimal(p2pFee));

        PeerToPeerTransaction peerToPeerTransaction = PeerToPeerTransaction.builder()
                .trn(trn)
                .amount(finalSentAmount)
                .transactionDate(LocalDateTime.now())
                .sender(sender)
                .receiver(receiver)
                .build();

        transactionService.save(peerToPeerTransaction);
        log.debug("Peer to peer transaction saved successfully with trn of {}", trn);
        return peerToPeerTransaction;
    }
}
