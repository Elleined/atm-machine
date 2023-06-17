package com.moneysender.Money.Sender.service;


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

    @Transactional
    public void withdraw(int userId, @NonNull BigDecimal amount) throws IllegalArgumentException {
        if (isAmountLessThanZero(amount)) {
            log.trace("Amount trying to withdraw is {} which is less than 0 or a negative number", amount);
            throw new IllegalArgumentException("Amount should be positive and cannot be zero!");
        }

        User user = userService.getById(userId);
        BigDecimal oldBalance = user.getBalance();
        BigDecimal newBalance = user.getBalance().subtract(amount);
        user.setBalance(newBalance);
        userService.save(user);
        log.debug("User with id of {} withdraw amounting {}.\nOld balance: {}\nNew Balance: {}", userId, amount, oldBalance, newBalance);
    }

    public boolean isAmountLessThanZero(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }
}
