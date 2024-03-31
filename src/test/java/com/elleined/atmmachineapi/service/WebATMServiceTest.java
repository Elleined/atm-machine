package com.elleined.atmmachineapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.machine.deposit.DepositService;
import com.elleined.atmmachineapi.service.machine.p2p.PeerToPeerService;
import com.elleined.atmmachineapi.service.machine.withdraw.WithdrawService;
import com.elleined.atmmachineapi.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class WebATMServiceTest {

    @Mock
    private DepositService depositService;
    @Mock
    private WithdrawService withdrawService;
    @Mock
    private PeerToPeerService peerToPeerService;
    @InjectMocks
    private WebATMService webATMService;

    @Test
    void deposit() {
        // Expected Value

        // Mock data
        User user = User.builder()
                .balance(new BigDecimal(100))
                .build();

        // Set up method

        // Stubbing methods
        when(depositService.deposit(any(User.class), any(BigDecimal.class))).thenReturn(new DepositTransaction());

        // Calling the method
        webATMService.deposit(user, new BigDecimal(100));

        // Behavior Verifications

        // Assertions
        verify(depositService).deposit(any(User.class), any(BigDecimal.class));
    }

    @Test
    void withdraw() {
        // Expected Value

        // Mock data
        User user = User.builder()
                .balance(new BigDecimal(100))
                .build();

        // Set up method

        // Stubbing methods
        when(withdrawService.withdraw(any(User.class), any(BigDecimal.class))).thenReturn(new WithdrawTransaction());

        // Calling the method
        webATMService.withdraw(user, new BigDecimal(100));

        // Behavior Verifications

        // Assertions
        verify(withdrawService).withdraw(any(User.class), any(BigDecimal.class));
    }

    @Test
    void peerToPeer() {
        // Expected Value

        // Mock data
        User sender = spy(User.class);
        sender.setId(1);
        sender.setBalance(new BigDecimal(1_000));
        sender.setSentMoneyTransactions(new ArrayList<>());
        sender.setWithdrawTransactions(new ArrayList<>());

        User receiver = User.builder()
                .id(2)
                .balance(new BigDecimal(1_000))
                .build();

        // Set up method

        // Stubbing methods
        when(peerToPeerService.peerToPeer(any(User.class), any(User.class), any(BigDecimal.class))).thenReturn(new PeerToPeerTransaction());

        // Calling the method
        webATMService.peerToPeer(sender, receiver, new BigDecimal(100));

        // Behavior Verifications

        // Assertions
        verify(peerToPeerService).peerToPeer(any(User.class), any(User.class), any(BigDecimal.class));
    }

}