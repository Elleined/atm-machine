package com.elleined.atmmachineapi.service.transaction.withdraw;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.repository.transaction.WithdrawTransactionRepository;
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
class WithdrawTransactionServiceImplTest {
    @Mock
    private WithdrawTransactionRepository withdrawTransactionRepository;
    @InjectMocks
    private WithdrawTransactionServiceImpl withdrawTransactionService;

    @Test
    void getById() {
        // Expected Value

        // Mock data
        WithdrawTransaction withdrawTransaction = new WithdrawTransaction();

        // Set up method

        // Stubbing methods
        when(withdrawTransactionRepository.findById(anyInt())).thenReturn(Optional.of(withdrawTransaction));

        // Calling the method
        withdrawTransactionService.getById(1);

        // Behavior Verifications

        // Assertions
        verify(withdrawTransactionRepository).findById(anyInt());
    }

    @Test
    void getAllById() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(withdrawTransactionRepository.findAllById(anySet())).thenReturn(new ArrayList<>());

        // Calling the method
        withdrawTransactionService.getAllById(new HashSet<>());

        // Behavior Verifications

        // Assertions
        verify(withdrawTransactionRepository).findAllById(anySet());
    }

    @Test
    void getAll() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        WithdrawTransaction withdrawTransaction1 = WithdrawTransaction.builder()
                .transactionDate(LocalDateTime.now().plusDays(1))
                .build();

        WithdrawTransaction withdrawTransaction2 = WithdrawTransaction.builder()
                .transactionDate(LocalDateTime.now().plusDays(2))
                .build();

        List<WithdrawTransaction> rawTransactions = List.of(withdrawTransaction1, withdrawTransaction2);
        currentUser.setWithdrawTransactions(rawTransactions);
        // Set up method
        List<WithdrawTransaction> expectedTransactions = List.of(withdrawTransaction2, withdrawTransaction1);

        // Stubbing methods

        // Calling the method
        List<WithdrawTransaction> actualTransactions = withdrawTransactionService.getAll(currentUser, );

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
        when(withdrawTransactionRepository.save(any(WithdrawTransaction.class))).thenReturn(new WithdrawTransaction());

        // Calling the method
        withdrawTransactionService.save(, new WithdrawTransaction(), );

        // Behavior Verifications
        verify(withdrawTransactionRepository).save(any(WithdrawTransaction.class));

        // Assertions
    }
}