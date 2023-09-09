package com.elleined.atmmachineapi.service.fee;

import com.elleined.atmmachineapi.model.AppWallet;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.repository.AppWalletRepository;
import com.elleined.atmmachineapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class FeeServiceImpl implements FeeService {

    private final UserRepository userRepository;
    private final AppWalletRepository appWalletRepository;

    @Override
    public float getWithdrawalFee(BigDecimal withdrawalAmount) {
        return (withdrawalAmount.intValue() * (WITHDRAWAL_FEE_PERCENTAGE / 100f));
    }

    @Override
    public float getDepositFee(BigDecimal depositedAmount) {
        return (depositedAmount.intValue() * (DEPOSIT_FEE_PERCENTAGE / 100f));
    }

    @Override
    public float getP2pFee(BigDecimal sentAmount) {
        return (sentAmount.intValue() * (P2P_FEE_PERCENTAGE / 100f));
    }

    @Override
    public void deductDepositFee(User currentUser, BigDecimal depositedAmount) {
        float depositFee = getDepositFee(depositedAmount);

        BigDecimal newBalance = currentUser.getBalance().subtract(new BigDecimal(depositFee));
        currentUser.setBalance(newBalance);

        AppWallet appWallet = appWalletRepository.findById(1).orElseThrow();
        BigDecimal oldAppWalletBalance = appWallet.getBalance();
        BigDecimal newAppWalletBalance = appWallet.getBalance().add(new BigDecimal(depositFee));
        appWallet.setBalance(newAppWalletBalance);

        userRepository.save(currentUser);
        appWalletRepository.save(appWallet);
        log.debug("User with id of {} deposited amount of {} and have a {} of deposit fee which is {}% of the deposited amount", currentUser.getId(), depositedAmount, depositFee, DEPOSIT_FEE_PERCENTAGE);
        log.debug("App wallet has new balance of {} from {}", appWallet.getBalance(), oldAppWalletBalance);
    }

    @Override
    public void deductWithdrawalFee(User currentUser, BigDecimal withdrawnAmount) {
        float withdrawalFee = getWithdrawalFee(withdrawnAmount);

        BigDecimal newBalance = currentUser.getBalance().subtract(new BigDecimal(withdrawalFee));
        currentUser.setBalance(newBalance);

        AppWallet appWallet = appWalletRepository.findById(1).orElseThrow();
        BigDecimal oldAppWalletBalance = appWallet.getBalance();
        BigDecimal newAppWalletBalance = appWallet.getBalance().add(new BigDecimal(WITHDRAWAL_FEE_PERCENTAGE));
        appWallet.setBalance(newAppWalletBalance);

        userRepository.save(currentUser);
        appWalletRepository.save(appWallet);
        log.debug("User with id of {} withdrawn amount of {} and have a {} of withdrawal fee which is {}% of the withdrawn amount", currentUser.getId(), withdrawnAmount, withdrawalFee, WITHDRAWAL_FEE_PERCENTAGE);
        log.debug("App wallet has new balance of {} from {}", appWallet.getBalance(), oldAppWalletBalance);
    }

    @Override
    public void deductP2pFee(User sender, User receiver, BigDecimal sentAmount) {
        float p2pFee = getP2pFee(sentAmount);

        BigDecimal senderNewBalance = sender.getBalance().subtract(new BigDecimal(p2pFee));
        sender.setBalance(senderNewBalance);

        BigDecimal receiverNewBalance = receiver.getBalance().subtract(new BigDecimal(p2pFee));
        receiver.setBalance(receiverNewBalance);

        AppWallet appWallet = appWalletRepository.findById(1).orElseThrow();
        BigDecimal oldAppWalletBalance = appWallet.getBalance();
        BigDecimal newAppWalletBalance = appWallet.getBalance().add(new BigDecimal(p2pFee * 2));
        appWallet.setBalance(newAppWalletBalance);

        userRepository.save(sender);
        userRepository.save(receiver);
        appWalletRepository.save(appWallet);

        log.debug("Sender with id of {} sent money amounting {} to receiver with id {} and both user are now deducted with p2p fee of {} which is the {}% of sent amount", sender.getId(), sentAmount, receiver.getId(), p2pFee, P2P_FEE_PERCENTAGE);
        log.debug("App wallet has new balance of {} from {}", appWallet.getBalance(), oldAppWalletBalance);
    }
}
