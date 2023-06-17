package com.moneysender.Money.Sender.service;

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
public class DepositService {
    private final UserService userService;
    private final ATMValidator atmValidator;

    @Transactional
    public BigDecimal deposit(int userId, @NonNull BigDecimal amount)
            throws IllegalArgumentException,
            ResourceNotFoundException {

        if (atmValidator.isValidAmount(amount)) {
            log.trace("Amount trying to deposit is {} which is less than 0 or a negative number", amount);
            throw new IllegalArgumentException("Amount should be positive and cannot be zero!");
        }
        User user = userService.getById(userId);
        BigDecimal oldBalance = user.getBalance();
        BigDecimal newBalance = user.getBalance().add(amount);
        user.setBalance(newBalance);
        userService.save(user);
        log.debug("User with id of {} deposited amounting {}.\nOld balance: {}\nNew Balance: {}", userId, amount, oldBalance, newBalance);
        return user.getBalance();
    }
}
