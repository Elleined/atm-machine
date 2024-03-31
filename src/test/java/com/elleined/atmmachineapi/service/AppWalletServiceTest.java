package com.elleined.atmmachineapi.service;

import com.elleined.atmmachineapi.model.AppWallet;
import com.elleined.atmmachineapi.repository.AppWalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppWalletServiceTest {

    @Mock
    private AppWalletRepository appWalletRepository;
    @InjectMocks
    private AppWalletService appWalletService;

    @Test
    void addAndSaveBalance() {
        // Expected Value
        BigDecimal expectedWalletBalance = new BigDecimal(200);

        // Mock data
        AppWallet appWallet = AppWallet.builder()
                .balance(new BigDecimal(100))
                .build();

        // Set up method

        // Stubbing methods
        when(appWalletRepository.findById(anyInt())).thenReturn(Optional.of(appWallet));
        when(appWalletRepository.save(any(AppWallet.class))).thenReturn(appWallet);

        // Calling the method
        appWalletService.addAndSaveBalance(100);

        // Behavior Verifications
        verify(appWalletRepository).findById(anyInt());
        verify(appWalletRepository).save(any(AppWallet.class));

        // Assertions
        assertEquals(expectedWalletBalance, appWallet.getBalance());
    }
}