package com.elleined.atmmachineapi.service.validator.deposit;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DepositValidatorTest {

    @Test
    void reachedLimitAmountPerDay() {
        User user = new User();
        DepositValidator depositValidator = new DepositValidator();

        DepositTransaction depositTransaction1 = DepositTransaction.builder()
                .amount(new BigDecimal(5000))
                .transactionDate(LocalDateTime.now())
                .build();

        DepositTransaction depositTransaction2 = DepositTransaction.builder()
                .amount(new BigDecimal(5000))
                .transactionDate(LocalDateTime.now())
                .build();
        List<DepositTransaction> depositTransactions = Arrays.asList(depositTransaction1, depositTransaction2);
        user.setDepositTransactions(depositTransactions);

        assertTrue(depositValidator.reachedLimitAmountPerDay(user));
    }

    @Test
    void isBelowMinimum() {
        // Expected Value
        DepositValidator depositValidator = new DepositValidator();

        // Mock data
        BigDecimal amount = new BigDecimal(499);

        // Set up method

        // Stubbing methods

        // Calling the method
        boolean isBelowMinimum = depositValidator.isBelowMinimum(amount);

        // Behavior Verifications

        // Assertions
        assertTrue(isBelowMinimum);
    }

    @Test
    void isAboveMaximum() {
        // Expected Value
        DepositValidator depositValidator = new DepositValidator();

        // Mock data
        BigDecimal amount = new BigDecimal(10_001);

        // Set up method

        // Stubbing methods

        // Calling the method
        boolean isAboveMaximum = depositValidator.isAboveMaximum(amount);

        // Behavior Verifications

        // Assertions
        assertTrue(isAboveMaximum);
    }
}