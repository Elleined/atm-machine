package com.elleined.atmmachineapi.service.atm;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.utils.TransactionUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class ATMValidator {

    public boolean isBalanceEnough(User sender, BigDecimal amountToBeSent) {
        return sender.getBalance().compareTo(amountToBeSent) < 0;
    }

    public boolean isValidAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }

    public boolean isSenderSendingToHimself(User sender, User receiver) {
        return sender.getId() == receiver.getId();
    }
}
