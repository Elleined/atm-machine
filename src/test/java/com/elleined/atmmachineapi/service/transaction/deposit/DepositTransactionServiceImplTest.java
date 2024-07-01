package com.elleined.atmmachineapi.service.transaction.deposit;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.repository.transaction.DepositTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositTransactionServiceImplTest {

    @Mock
    private DepositTransactionRepository depositTransactionRepository;
    @InjectMocks
    private DepositTransactionServiceImpl depositTransactionService;

    @Test
    void getById() {
        // Expected Value

        // Mock data
        DepositTransaction depositTransaction = new DepositTransaction();

        // Set up method

        // Stubbing methods
        when(depositTransactionRepository.findById(anyInt())).thenReturn(Optional.of(depositTransaction));

        // Calling the method
        depositTransactionService.getById(1);

        // Behavior Verifications

        // Assertions
        verify(depositTransactionRepository).findById(anyInt());
    }

    @Test
    void getAllById() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(depositTransactionRepository.findAllById(anySet())).thenReturn(new ArrayList<>());

        // Calling the method
        depositTransactionService.getAllById(new HashSet<>());

        // Behavior Verifications

        // Assertions
        verify(depositTransactionRepository).findAllById(anySet());
    }

    @Test
    void getAll() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        DepositTransaction depositTransaction1 = DepositTransaction.builder()
                .transactionDate(LocalDateTime.now().plusDays(1))
                .build();

        DepositTransaction depositTransaction2 = DepositTransaction.builder()
                .transactionDate(LocalDateTime.now().plusDays(2))
                .build();

        List<DepositTransaction> rawTransactions = List.of(depositTransaction1, depositTransaction2);
        currentUser.setDepositTransactions(rawTransactions);
        // Set up method
        List<DepositTransaction> expectedTransactions = List.of(depositTransaction2, depositTransaction1);

        // Stubbing methods

        // Calling the method
        List<DepositTransaction> actualTransactions = depositTransactionService.getAll(currentUser, );

        // Behavior Verifications

        // Assertions
        assertIterableEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void save() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(depositTransactionRepository.save(any(DepositTransaction.class))).thenReturn(new DepositTransaction());

        // Calling the method
        depositTransactionService.save(, new DepositTransaction(), );

        // Behavior Verifications
        verify(depositTransactionRepository).save(any(DepositTransaction.class));

        // Assertions
    }
}