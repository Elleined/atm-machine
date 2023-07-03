package com.moneysender.Money.Sender.service;


import com.moneysender.Money.Sender.exception.InsufficientFundException;
import com.moneysender.Money.Sender.exception.ResourceNotFoundException;
import com.moneysender.Money.Sender.model.User;
import com.moneysender.Money.Sender.model.WithdrawTransaction;
import com.moneysender.Money.Sender.repository.WithdrawTransactionRepository;
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

    private final UserService userService;
    private final ATMValidator atmValidator;
    private final WithdrawTransactionRepository withdrawTransactionRepository;

    @Transactional
    public BigDecimal withdraw(int userId, @NonNull BigDecimal amount)
            throws IllegalArgumentException,
            InsufficientFundException,
            ResourceNotFoundException {

        if (atmValidator.isValidAmount(amount)) {
            log.trace("Amount trying to send is {} which is less than 0 or a negative number", amount);
            throw new IllegalArgumentException("Amount should be positive and cannot be zero!");
        }
        User user = userService.getById(userId);
        if (atmValidator.isBalanceEnough(user, amount)) {
            log.trace("User balance is {} and trying to withdraw {} which is not enough!", user.getBalance(), amount);
            throw new InsufficientFundException("Insufficient Funds!");
        }

        BigDecimal oldBalance = user.getBalance();
        BigDecimal newBalance = user.getBalance().subtract(amount);
        user.setBalance(newBalance);
        userService.save(user);

        saveWithdrawTransaction(user, amount);

        log.debug("User with id of {} withdraw amounting {}.\nOld balance: {}\nNew Balance: {}", userId, amount, oldBalance, newBalance);
        return user.getBalance();
    }

    @Transactional
    private void saveWithdrawTransaction(User user, @NonNull BigDecimal withdrawalAmount) {
        String trn = UUID.randomUUID().toString();
        WithdrawTransaction withdrawTransaction = WithdrawTransaction.builder()
                .user(user)
                .amount(withdrawalAmount)
                .withdrawalDate(LocalDateTime.now())
                .trn(trn)
                .accountBalance(user.getBalance())
                .build();

        withdrawTransactionRepository.save(withdrawTransaction);
        log.debug("Deposit transaction saved with trn of {}", trn);
    }
}
