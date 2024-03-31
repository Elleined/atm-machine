package com.elleined.atmmachineapi.service.machine.p2p;

import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.SendingToHimselfException;
import com.elleined.atmmachineapi.exception.amount.NotValidAmountException;
import com.elleined.atmmachineapi.exception.limit.LimitException;
import com.elleined.atmmachineapi.exception.limit.LimitExceptionPerDayException;
import com.elleined.atmmachineapi.mapper.transaction.PeerToPeerTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.request.transaction.PeerToPeerTransactionRequest;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.transaction.p2p.PeerToPeerTransactionService;
import com.elleined.atmmachineapi.service.validator.p2p.PeerToPeerValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PeerToPeerServiceImpl implements PeerToPeerService {
    private final UserRepository userRepository;

    private final PeerToPeerTransactionService peerToPeerTransactionService;
    private final PeerToPeerTransactionMapper peerToPeerTransactionMapper;
    private final PeerToPeerValidator peerToPeerValidator;

    private final FeeService feeService;
    private final AppWalletService appWalletService;

    @Override
    public PeerToPeerTransaction peerToPeer(User sender, User receiver, @NonNull BigDecimal sentAmount)
            throws SendingToHimselfException,
            InsufficientFundException,
            NotValidAmountException,
            LimitException {

        if (sender.isSendingToHimSelf(receiver)) throw new SendingToHimselfException("You cannot send to yourself");
        if (peerToPeerValidator.isNotValidAmount(sentAmount)) throw new NotValidAmountException("Cannot send money! because amount should be positive and cannot be zero!");
        if (sender.isBalanceNotEnough(sentAmount)) throw new InsufficientFundException("Insufficient Funds!");
        if (peerToPeerValidator.reachedLimitAmountPerDay(sender)) throw new LimitExceptionPerDayException("Cannot send money! Because you already reached the sent amount limit per day which is " + PeerToPeerValidator.PEER_TO_PEER_LIMIT_PER_DAY);

        float p2pFee = feeService.getP2pFee(sentAmount);
        BigDecimal finalSentAmount = sentAmount.subtract(new BigDecimal(p2pFee));
        BigDecimal senderOldBalance = sender.getBalance();
        BigDecimal receiverOldBalance = receiver.getBalance();

        updateSenderBalance(sender, sentAmount);
        updateRecipientBalance(receiver, finalSentAmount);
        appWalletService.addAndSaveBalance(p2pFee);

        PeerToPeerTransactionRequest peerToPeerTransactionRequest = PeerToPeerTransactionRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(sentAmount)
                .build();

        PeerToPeerTransaction peerToPeerTransaction = peerToPeerTransactionMapper.toEntity(peerToPeerTransactionRequest);
        peerToPeerTransactionService.save(peerToPeerTransaction);
        log.debug("Sender with id of {} sent money amounting {} from {} because of p2p fee of {} which is the {}% of sent amount.", sender.getId(), finalSentAmount, sentAmount, p2pFee, FeeService.P2P_FEE_PERCENTAGE);
        log.debug("Sender with id of {} has now new balance of {} from {}.", sender.getId(), sender.getBalance(), senderOldBalance);
        log.debug("Receiver with id of {} has now new balance of {} from {}", receiver.getId(), receiver.getBalance(), receiverOldBalance);
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
}
