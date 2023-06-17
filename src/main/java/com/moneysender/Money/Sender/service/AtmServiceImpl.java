package com.moneysender.Money.Sender.service;

import com.moneysender.Money.Sender.exception.InsufficientFundException;
import com.moneysender.Money.Sender.exception.ResourceNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AtmServiceImpl implements AtmService {
    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final MoneySenderService moneySenderService;

    @Override
    public void deposit(int userId, @NonNull BigDecimal amount) {
        depositService.deposit(userId, amount);
    }

    @Override
    public void withdraw(int userId, @NonNull BigDecimal amount) {
        withdrawService.withdraw(userId, amount);
    }

    @Override
    public void sendMoney(int senderId, @NonNull BigDecimal amount, int recipientId) {
		try {
			moneySenderService.sendMoney(1, new BigDecimal(100), 1);
		} catch (IllegalArgumentException | ResourceNotFoundException | InsufficientFundException e) {
			log.error("Error Occurred! {}", e.getMessage());
		}
    }
}
