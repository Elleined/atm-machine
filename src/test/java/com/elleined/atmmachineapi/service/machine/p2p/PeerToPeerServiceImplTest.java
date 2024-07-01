package com.elleined.atmmachineapi.service.machine.p2p;

import com.elleined.atmmachineapi.mapper.transaction.PeerToPeerTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.request.transaction.PeerToPeerTransactionRequest;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.transaction.p2p.PeerToPeerTransactionService;
import com.elleined.atmmachineapi.service.validator.p2p.PeerToPeerValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeerToPeerServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PeerToPeerTransactionService peerToPeerTransactionService;
    @Mock
    private PeerToPeerTransactionMapper peerToPeerTransactionMapper;
    @Mock
    private PeerToPeerValidator peerToPeerValidator;
    @Mock
    private FeeService feeService;
    @Mock
    private AppWalletService appWalletService;
    @InjectMocks
    private PeerToPeerServiceImpl peerToPeerService;

    @Test
    void peerToPeer() {
        // Expected Value
        BigDecimal senderExpectedBalance = new BigDecimal(500);
        BigDecimal receiverExpectedBalance = new BigDecimal(1_495);

        // Mock data
        BigDecimal senderSentAmount = new BigDecimal(500);
        float p2pFee = 5;

        User sender = spy(User.class);
        sender.setId(1);
        sender.setBalance(new BigDecimal(1_000));
        sender.setSentMoneyTransactions(new ArrayList<>());
        sender.setWithdrawTransactions(new ArrayList<>());

        User receiver = User.builder()
                .id(2)
                .balance(new BigDecimal(1_000))
                .build();

        PeerToPeerTransaction peerToPeerTransaction = new PeerToPeerTransaction();
        // Set up method

        // Stubbing methods
        doReturn(false).when(sender).isSendingToHimSelf(any(User.class));
        doReturn(false).when(sender).isBalanceNotEnough(any());
        when(peerToPeerValidator.isNotValidAmount(any(BigDecimal.class))).thenReturn(false);
        when(peerToPeerValidator.reachedLimitAmountPerDay(any(User.class))).thenReturn(false);
        when(feeService.getP2pFee(any(BigDecimal.class))).thenReturn(p2pFee);
        when(userRepository.save(any(User.class))).thenReturn(new User());
        doNothing().when(appWalletService).addAndSaveBalance(any());
        when(peerToPeerTransactionMapper.toEntity(any(PeerToPeerTransactionRequest.class))).thenReturn(peerToPeerTransaction);
        when(peerToPeerTransactionService.save(, any(PeerToPeerTransaction.class), )).thenReturn(peerToPeerTransaction);

        // Calling the method
        peerToPeerService.peerToPeer(sender, receiver, senderSentAmount);

        // Behavior Verifications
        verify(peerToPeerValidator).isNotValidAmount(any(BigDecimal.class));
        verify(peerToPeerValidator).reachedLimitAmountPerDay(any(User.class));
        verify(feeService).getP2pFee(any(BigDecimal.class));
        verify(userRepository, times(2)).save(any(User.class));
        verify(appWalletService).addAndSaveBalance(any());
        verify(peerToPeerTransactionMapper).toEntity(any(PeerToPeerTransactionRequest.class));
        verify(peerToPeerTransactionService).save(, any(PeerToPeerTransaction.class), );

        // Assertions
        assertEquals(senderExpectedBalance, sender.getBalance());
        assertEquals(receiverExpectedBalance, receiver.getBalance());
    }
}