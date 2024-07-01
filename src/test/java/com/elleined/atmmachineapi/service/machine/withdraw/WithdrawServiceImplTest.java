package com.elleined.atmmachineapi.service.machine.withdraw;

import com.elleined.atmmachineapi.mapper.transaction.WithdrawTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.request.transaction.WithdrawTransactionRequest;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.transaction.withdraw.WithdrawTransactionServiceImpl;
import com.elleined.atmmachineapi.service.validator.withdraw.WithdrawValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WithdrawServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private WithdrawTransactionServiceImpl withdrawTransactionService;
    @Mock
    private WithdrawTransactionMapper withdrawTransactionMapper;
    @Mock
    private WithdrawValidator withdrawValidator;
    @Mock
    private FeeService feeService;
    @Mock
    private AppWalletService appWalletService;
    @InjectMocks
    private WithdrawServiceImpl withdrawService;

    @Test
    void withdraw() {
        // Expected Value
        BigDecimal expectedBalance = new BigDecimal(100);

        // Mock data
        BigDecimal withdrawalAmount = new BigDecimal(100);
        float withdrawalFee = 10;
        User currentUser = spy(User.class);
        currentUser.setBalance(new BigDecimal(200));

        WithdrawTransaction withdrawTransaction = new WithdrawTransaction();
        // Set up method

        // Stubbing methods
        when(withdrawValidator.isNotValidAmount(any(BigDecimal.class))).thenReturn(false);
        doReturn(false).when(currentUser).isBalanceNotEnough(any());
        when(withdrawValidator.isBelowMinimum(any(BigDecimal.class))).thenReturn(false);
        when(withdrawValidator.isAboveMaximum(any(BigDecimal.class))).thenReturn(false);
        when(withdrawValidator.reachedLimitAmountPerDay(any(User.class))).thenReturn(false);
        when(feeService.getWithdrawalFee(any(BigDecimal.class))).thenReturn(withdrawalFee);
        when(userRepository.save(any(User.class))).thenReturn(currentUser);
        doNothing().when(appWalletService).addAndSaveBalance(any());
        when(withdrawTransactionMapper.toEntity(any(WithdrawTransactionRequest.class))).thenReturn(withdrawTransaction);
        when(withdrawTransactionService.save(, any(WithdrawTransaction.class), )).thenReturn(withdrawTransaction);

        // Calling the method
        withdrawService.withdraw(currentUser, withdrawalAmount);

        // Behavior Verifications
        verify(withdrawValidator).isNotValidAmount(any(BigDecimal.class));
        verify(withdrawValidator).isBelowMinimum(any(BigDecimal.class));
        verify(withdrawValidator).isAboveMaximum(any(BigDecimal.class));
        verify(withdrawValidator).reachedLimitAmountPerDay(any(User.class));
        verify(feeService).getWithdrawalFee(any(BigDecimal.class));
        verify(userRepository).save(any(User.class));
        verify(appWalletService).addAndSaveBalance(any());
        verify(withdrawTransactionMapper).toEntity(any(WithdrawTransactionRequest.class));
        verify(withdrawTransactionService).save(, any(WithdrawTransaction.class), );

        // Assertions
        assertEquals(expectedBalance, currentUser.getBalance());
    }
}