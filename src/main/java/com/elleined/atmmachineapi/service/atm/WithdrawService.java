package com.elleined.atmmachineapi.service.atm;


import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.atm.transaction.TransactionService;
import com.elleined.atmmachineapi.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WithdrawService {
    private final UserServiceImpl userServiceImpl;
    private final ATMValidator atmValidator;
    private final TransactionService transactionService;

    public BigDecimal withdraw(int currentUserId, @NonNull BigDecimal withdrawalAmount)
            throws IllegalArgumentException,
            InsufficientFundException,
            ResourceNotFoundException {

        User currentUser = userServiceImpl.getById(currentUserId);
        if (atmValidator.isValidAmount(withdrawalAmount)) throw new IllegalArgumentException("Amount should be positive and cannot be zero!");
        if (atmValidator.isBalanceEnough(currentUser, withdrawalAmount)) throw new InsufficientFundException("Insufficient Funds!");

        BigDecimal oldBalance = currentUser.getBalance();
        BigDecimal newBalance = currentUser.getBalance().subtract(withdrawalAmount);
        currentUser.setBalance(newBalance);
        userServiceImpl.save(currentUser);

        saveWithdrawTransaction(currentUser, withdrawalAmount);
        log.debug("User with id of {} withdraw amounting {}.\nOld balance: {}\nNew Balance: {}", currentUser.getId(), withdrawalAmount, oldBalance, newBalance);
        return currentUser.getBalance();
    }

    private void saveWithdrawTransaction(@NonNull User user, @NonNull BigDecimal withdrawalAmount) {
        String trn = UUID.randomUUID().toString();

        Transaction withdrawTransaction = WithdrawTransaction.builder()
                .trn(trn)
                .amount(withdrawalAmount)
                .transactionDate(LocalDateTime.now())
                .user(user)
                .accountBalance(user.getBalance())
                .build();

        transactionService.save(withdrawTransaction);
        log.debug("Deposit transaction saved with trn of {}", trn);
    }
}
