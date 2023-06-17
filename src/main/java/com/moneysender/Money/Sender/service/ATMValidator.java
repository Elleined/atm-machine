package com.moneysender.Money.Sender.service;

import com.moneysender.Money.Sender.model.User;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ATMValidator {

    public boolean isBalanceEnough(@NonNull User sender, @NonNull BigDecimal amountToBeSent) {
        return sender.getBalance().compareTo(amountToBeSent) < 0;
    }

    public boolean isValidAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }
}
