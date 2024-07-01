package com.elleined.atmmachineapi.service.transaction.p2p;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.repository.transaction.PeerToPeerTransactionRepository;
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
class PeerToPeerTransactionServiceImplTest {
    @Mock
    private PeerToPeerTransactionRepository peerToPeerTransactionRepository;
    @InjectMocks
    private PeerToPeerTransactionServiceImpl peerToPeerTransactionService;


    @Test
    void getById() {
        // Expected Value

        // Mock data
        PeerToPeerTransaction peerToPeerTransaction = new PeerToPeerTransaction();

        // Set up method

        // Stubbing methods
        when(peerToPeerTransactionRepository.findById(anyInt())).thenReturn(Optional.of(peerToPeerTransaction));

        // Calling the method
        peerToPeerTransactionService.getById(1);

        // Behavior Verifications

        // Assertions
        verify(peerToPeerTransactionRepository).findById(anyInt());
    }

    @Test
    void getAllById() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(peerToPeerTransactionRepository.findAllById(anySet())).thenReturn(new ArrayList<>());

        // Calling the method
        peerToPeerTransactionService.getAllById(new HashSet<>());

        // Behavior Verifications

        // Assertions
        verify(peerToPeerTransactionRepository).findAllById(anySet());
    }

    @Test
    void save() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(peerToPeerTransactionRepository.save(any(PeerToPeerTransaction.class))).thenReturn(new PeerToPeerTransaction());

        // Calling the method
        peerToPeerTransactionService.save(, new PeerToPeerTransaction(), );

        // Behavior Verifications
        verify(peerToPeerTransactionRepository).save(any(PeerToPeerTransaction.class));

        // Assertions
    }
    @Test
    void getAll() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method

        // Behavior Verifications

        // Assertions
    }

    @Test
    void getAllReceiveMoneyTransactions() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        PeerToPeerTransaction peerToPeerTransaction1 = PeerToPeerTransaction.builder()
                .transactionDate(LocalDateTime.now().plusDays(1))
                .build();

        PeerToPeerTransaction peerToPeerTransaction2 = PeerToPeerTransaction.builder()
                .transactionDate(LocalDateTime.now().plusDays(2))
                .build();

        List<PeerToPeerTransaction> rawTransactions = List.of(peerToPeerTransaction1, peerToPeerTransaction2);
        currentUser.setReceiveMoneyTransactions(rawTransactions);

        // Set up method
        List<PeerToPeerTransaction> expectedTransactions = List.of(peerToPeerTransaction2, peerToPeerTransaction1);

        // Stubbing methods

        // Calling the method
        List<PeerToPeerTransaction> actualTransactions = peerToPeerTransactionService.getAllReceiveMoneyTransactions(currentUser);

        // Behavior Verifications

        // Assertions
        assertIterableEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void getAllSentMoneyTransactions() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        PeerToPeerTransaction peerToPeerTransaction1 = PeerToPeerTransaction.builder()
                .transactionDate(LocalDateTime.now().plusDays(1))
                .build();

        PeerToPeerTransaction peerToPeerTransaction2 = PeerToPeerTransaction.builder()
                .transactionDate(LocalDateTime.now().plusDays(2))
                .build();

        List<PeerToPeerTransaction> rawTransactions = List.of(peerToPeerTransaction1, peerToPeerTransaction2);
        currentUser.setSentMoneyTransactions(rawTransactions);
        // Set up method
        List<PeerToPeerTransaction> expectedTransactions = List.of(peerToPeerTransaction2, peerToPeerTransaction1);

        // Stubbing methods

        // Calling the method
        List<PeerToPeerTransaction> actualTransactions = peerToPeerTransactionService.getAllSentMoneyTransactions(currentUser);

        // Behavior Verifications

        // Assertions
        assertIterableEquals(expectedTransactions, actualTransactions);
    }
}