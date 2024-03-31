package com.elleined.atmmachineapi.service.validator.withdraw;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class WithdrawValidatorTest {


    @Test
    void reachedLimitAmountPerDay() {
        User user = new User();
        WithdrawValidator withdrawValidator = new WithdrawValidator();

        WithdrawTransaction depositTransaction1 = WithdrawTransaction.builder()
                .amount(new BigDecimal(5000))
                .transactionDate(LocalDateTime.now())
                .build();

        WithdrawTransaction depositTransaction2 = WithdrawTransaction.builder()
                .amount(new BigDecimal(5000))
                .transactionDate(LocalDateTime.now())
                .build();
        List<WithdrawTransaction> depositTransactions = Arrays.asList(depositTransaction1, depositTransaction2);
        user.setWithdrawTransactions(depositTransactions);

        assertTrue(withdrawValidator.reachedLimitAmountPerDay(user));
    }

    @Test
    void isBelowMinimum() {
        // Expected Value
        WithdrawValidator withdrawValidator = new WithdrawValidator();

        // Mock data
        BigDecimal amount = new BigDecimal(499);

        // Set up method

        // Stubbing methods

        // Calling the method
        boolean isBelowMinimum = withdrawValidator.isBelowMinimum(amount);

        // Behavior Verifications

        // Assertions
        assertTrue(isBelowMinimum);
    }

    @Test
    void isAboveMaximum() {
        // Expected Value
        WithdrawValidator withdrawValidator = new WithdrawValidator();

        // Mock data
        BigDecimal amount = new BigDecimal(10_001);

        // Set up method

        // Stubbing methods

        // Calling the method
        boolean isAboveMaximum = withdrawValidator.isAboveMaximum(amount);

        // Behavior Verifications

        // Assertions
        assertTrue(isAboveMaximum);
    }

}