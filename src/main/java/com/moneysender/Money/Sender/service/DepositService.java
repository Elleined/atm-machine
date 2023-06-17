package com.moneysender.Money.Sender.service;

import com.moneysender.Money.Sender.exception.ResourceNotFoundException;
import com.moneysender.Money.Sender.model.DepositTransaction;
import com.moneysender.Money.Sender.model.User;
import com.moneysender.Money.Sender.repository.DepositTransactionRepository;
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
public class DepositService {
    private final UserService userService;
    private final ATMValidator atmValidator;
    private final DepositTransactionRepository depositTransactionRepository;

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

        saveDepositTransaction(user, amount);

        log.debug("User with id of {} deposited amounting {}.\nOld balance: {}\nNew Balance: {}", userId, amount, oldBalance, newBalance);
        return user.getBalance();
    }

    @Transactional
    private void saveDepositTransaction(User user, @NonNull BigDecimal depositedAmount) {
        String trn = UUID.randomUUID().toString();
        DepositTransaction depositTransaction = DepositTransaction.builder()
                .user(user)
                .amount(depositedAmount)
                .depositDate(LocalDateTime.now())
                .trn(trn)
                .build();

        depositTransactionRepository.save(depositTransaction);
        log.debug("Deposit transaction saved with trn of {}", trn);
    }
}
