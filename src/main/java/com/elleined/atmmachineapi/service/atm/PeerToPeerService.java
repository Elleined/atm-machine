package com.elleined.atmmachineapi.service.atm;

import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.NotValidAmountException;
import com.elleined.atmmachineapi.exception.SendingToHimselfException;
import com.elleined.atmmachineapi.exception.limit.LimitException;
import com.elleined.atmmachineapi.exception.limit.PeerToPeerLimitPerDayException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.atm.transaction.TransactionService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.utils.TransactionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PeerToPeerService {
    int PEER_TO_PEER_LIMIT_PER_DAY = 10_000;

    private final UserRepository userRepository;
    private final FeeService feeService;
    private final ATMValidator atmValidator;
    private final TransactionService transactionService;
    private final AppWalletService appWalletService;

    public PeerToPeerTransaction peerToPeer(User sender, User receiver, @NonNull BigDecimal sentAmount)
            throws SendingToHimselfException,
            InsufficientFundException,
            NotValidAmountException,
            LimitException {

        if (atmValidator.isSenderSendingToHimself(sender, receiver)) throw new SendingToHimselfException("You cannot send to yourself");
        if (atmValidator.isValidAmount(sentAmount)) throw new NotValidAmountException("Amount should be positive and cannot be zero!");
        if (atmValidator.isBalanceEnough(sender, sentAmount)) throw new InsufficientFundException("Insufficient Funds!");
        if (isSentAmountAboveLimit(sentAmount)) throw new LimitException("You cannot send money that is greater than sent amount limit which is " + PEER_TO_PEER_LIMIT_PER_DAY);
        if (isSenderReachedSentLimitPerDay(sender)) throw new PeerToPeerLimitPerDayException("Cannot sent money! Because you already reached the sent amount limit per day which is " + PEER_TO_PEER_LIMIT_PER_DAY);

        float p2pFee = feeService.getP2pFee(sentAmount);
        BigDecimal finalSentAmount = feeService.deductP2pFee(sentAmount, p2pFee);
        BigDecimal senderOldBalance = sender.getBalance();
        BigDecimal receiverOldBalance = receiver.getBalance();

        updateSenderBalance(sender, sentAmount);
        updateRecipientBalance(receiver, finalSentAmount);
        appWalletService.addAndSaveBalance(p2pFee);
        PeerToPeerTransaction peerToPeerTransaction = savePeerToPeerTransaction(sender, receiver, sentAmount);

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

    private PeerToPeerTransaction savePeerToPeerTransaction(User sender, User receiver, BigDecimal sentAmount) {
        String trn = UUID.randomUUID().toString();

        PeerToPeerTransaction peerToPeerTransaction = PeerToPeerTransaction.builder()
                .trn(trn)
                .amount(sentAmount)
                .transactionDate(LocalDateTime.now())
                .sender(sender)
                .receiver(receiver)
                .build();

        transactionService.save(peerToPeerTransaction);
        log.debug("Peer to peer transaction saved successfully with trn of {}", trn);
        return peerToPeerTransaction;
    }

    public boolean isSentAmountAboveLimit(BigDecimal sentAmount) {
        return sentAmount.compareTo(new BigDecimal(PEER_TO_PEER_LIMIT_PER_DAY)) > 0;
    }

    public boolean isSenderReachedSentLimitPerDay(User currentUser) {
        final LocalDateTime currentDateTimeMidnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        final LocalDateTime tomorrowMidnight = currentDateTimeMidnight.plusDays(1);
        List<PeerToPeerTransaction> userPeerToPeerTransactions = currentUser.getSentMoneyTransactions();
        List<PeerToPeerTransaction>  peerToPeerTransactions =
                TransactionUtils.getTransactionsByDateRange(userPeerToPeerTransactions, currentDateTimeMidnight, tomorrowMidnight);

        BigDecimal totalSentAmount = peerToPeerTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElseGet(() -> new BigDecimal(0));
        int comparisonResult = totalSentAmount.compareTo(new BigDecimal(PEER_TO_PEER_LIMIT_PER_DAY));
        return comparisonResult >= 0;
    }
}
