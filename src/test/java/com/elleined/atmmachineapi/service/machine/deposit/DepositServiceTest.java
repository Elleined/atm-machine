package com.elleined.atmmachineapi.service.machine.deposit;

import static org.junit.jupiter.api.Assertions.*;

import com.elleined.atmmachineapi.mapper.transaction.DepositTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.validator.ATMValidator;
import com.elleined.atmmachineapi.service.transaction.deposit.DepositTransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {

    @Mock
    private ATMValidator atmValidator;
    @Mock
    private DepositTransactionService depositTransactionService;
    @Mock
    private DepositTransactionMapper depositTransactionMapper;
    @Mock
    private FeeService feeService;
    @Mock
    private AppWalletService appWalletService;

    @InjectMocks
    private DepositService depositService;

    @Test
    void deposit() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method

        // Behavior Verifications

        // Assertions
    }

    @Test
    void reachedLimitAmountPerDay() {
        User user = new User();

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

        assertTrue(depositService.reachedLimitAmountPerDay(user));
    }

    @Test
    void isBelowMinimum() {
        // Expected Value

        // Mock data
        BigDecimal amount = new BigDecimal(499);

        // Set up method

        // Stubbing methods

        // Calling the method
        boolean isBelowMinimum = depositService.isBelowMinimum(amount);

        // Behavior Verifications

        // Assertions
        assertTrue(isBelowMinimum);
    }

    @Test
    void isAboveMaximum() {
        // Expected Value

        // Mock data
        BigDecimal amount = new BigDecimal(10_001);

        // Set up method

        // Stubbing methods

        // Calling the method
        boolean isAboveMaximum = depositService.isAboveMaximum(amount);

        // Behavior Verifications

        // Assertions
        assertTrue(isAboveMaximum);
    }
}