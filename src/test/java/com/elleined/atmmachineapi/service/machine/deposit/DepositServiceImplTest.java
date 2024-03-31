package com.elleined.atmmachineapi.service.machine.deposit;

import com.elleined.atmmachineapi.mapper.transaction.DepositTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.request.transaction.DepositTransactionRequest;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.transaction.deposit.DepositTransactionService;
import com.elleined.atmmachineapi.service.validator.deposit.DepositValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositServiceImplTest {
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
        BigDecimal expectedBalance = new BigDecimal(190);

        // Mock data
        BigDecimal depositAmount = new BigDecimal(100);
        float depositFee = 10;

        User currentUser = User.builder()
                .balance(new BigDecimal(100))
                .build();

        DepositTransaction depositTransaction = new DepositTransaction();
        // Set up method

        // Stubbing methods
        when(depositValidator.isNotValidAmount(any(BigDecimal.class))).thenReturn(false);
        when(depositValidator.isBelowMinimum(any(BigDecimal.class))).thenReturn(false);
        when(depositValidator.isAboveMaximum(any(BigDecimal.class))).thenReturn(false);
        when(depositValidator.reachedLimitAmountPerDay(any(User.class))).thenReturn(false);
        when(feeService.getDepositFee(any(BigDecimal.class))).thenReturn(depositFee);
        when(userRepository.save(any(User.class))).thenReturn(currentUser);
        doNothing().when(appWalletService).addAndSaveBalance(any());
        when(depositTransactionMapper.toEntity(any(DepositTransactionRequest.class))).thenReturn(depositTransaction);
        when(depositTransactionService.save(any(DepositTransaction.class))).thenReturn(depositTransaction);

        // Calling the method
        depositService.deposit(currentUser, depositAmount);

        // Behavior Verifications
        verify(depositValidator).isNotValidAmount(any(BigDecimal.class));
        verify(depositValidator).isBelowMinimum(any(BigDecimal.class));
        verify(depositValidator).isAboveMaximum(any(BigDecimal.class));
        verify(depositValidator).reachedLimitAmountPerDay(any(User.class));
        verify(feeService).getDepositFee(any(BigDecimal.class));
        verify(userRepository).save(any(User.class));
        verify(appWalletService).addAndSaveBalance(any());
        verify(depositTransactionMapper).toEntity(any(DepositTransactionRequest.class));
        verify(depositTransactionService).save(any(DepositTransaction.class));

        // Assertions
        assertEquals(expectedBalance, currentUser.getBalance());
    }
}