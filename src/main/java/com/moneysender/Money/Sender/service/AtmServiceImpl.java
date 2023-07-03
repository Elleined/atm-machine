package com.moneysender.Money.Sender.service;

import com.moneysender.Money.Sender.exception.InsufficientFundException;
import com.moneysender.Money.Sender.exception.ResourceNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AtmServiceImpl implements AtmService {
    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final MoneySenderService moneySenderService;

    @Override
    public void deposit(int userId, @NonNull BigDecimal amount) {
        try {
            BigDecimal newBalance = depositService.deposit(userId, amount);
            System.out.println("You successfully deposited amounting " + amount);
            System.out.println("=== Your new Account balance is " + newBalance + " ===");
        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            log.error("Error Occurred! {}", e.getMessage());
        }
    }

    @Override
    public void withdraw(int userId, @NonNull BigDecimal amount) {
        try {
            BigDecimal newBalance = withdrawService.withdraw(userId, amount);
            System.out.println("You successfully withdrawn amounting " + amount);
            System.out.println("=== Your new Account balance is " + newBalance + " ===");
        } catch (IllegalArgumentException | ResourceNotFoundException | InsufficientFundException e) {
            log.error("Error Occurred! {}", e.getMessage());
        }
    }

    @Override
    public void sendMoney(int senderId, @NonNull BigDecimal amount, int recipientId) {
		try {
			BigDecimal newBalance = moneySenderService.sendMoney(senderId, amount, recipientId);
            System.out.println("You successfully send money to the recipient with id of " + recipientId + " amounting " + amount);
            System.out.println("=== Your new Account balance is " + newBalance + " ===");
		} catch (IllegalArgumentException | ResourceNotFoundException | InsufficientFundException e) {
			log.error("Error Occurred! {}", e.getMessage());
		}
    }
}
