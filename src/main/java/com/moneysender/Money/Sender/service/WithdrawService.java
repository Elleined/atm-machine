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
@RequiredArgsConstructor
@Slf4j
public class WithdrawService {

    private final UserService userService;
    private final ATMValidator atmValidator;

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
        log.debug("User with id of {} withdraw amounting {}.\nOld balance: {}\nNew Balance: {}", userId, amount, oldBalance, newBalance);
        return user.getBalance();
    }
}
