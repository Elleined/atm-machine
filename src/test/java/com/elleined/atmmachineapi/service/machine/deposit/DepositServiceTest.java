package com.elleined.atmmachineapi.service.machine.deposit;

import com.elleined.atmmachineapi.mapper.transaction.DepositTransactionMapper;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.transaction.deposit.DepositTransactionService;
import com.elleined.atmmachineapi.service.validator.deposit.DepositValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private DepositTransactionService depositTransactionService;
    @Mock
    private DepositTransactionMapper depositTransactionMapper;
    @Mock
    private DepositValidator depositValidator;
    @Mock
    private FeeService feeService;
    @Mock
    private AppWalletService appWalletService;

    @InjectMocks
    private DepositServiceImpl depositService;

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
}