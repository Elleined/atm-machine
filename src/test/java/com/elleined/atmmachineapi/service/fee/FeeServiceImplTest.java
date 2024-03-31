package com.elleined.atmmachineapi.service.fee;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class FeeServiceImplTest {


    @Test
    void getWithdrawalFee() {
        FeeService atmFeeService = new FeeServiceImpl();

        BigDecimal amount = new BigDecimal(50);
        float actual = atmFeeService.getWithdrawalFee(amount);
        float expected = 1;

        assertEquals(expected, actual, "Failed because the expected withdrawal fee " + expected + " is not the " + FeeService.WITHDRAWAL_FEE_PERCENTAGE + "% of " + amount);
    }

    @Test
    void getDepositFee() {
        FeeService atmFeeService = new FeeServiceImpl();

        BigDecimal amount = new BigDecimal(50);
        float actual = atmFeeService.getDepositFee(amount);
        float expected = 1;

        assertEquals(expected, actual, "Failed because the expected deposit fee " + expected + " is not the " + FeeService.DEPOSIT_FEE_PERCENTAGE + "% of " + amount);
    }

    @Test
    void getP2pFee() {
        FeeService atmFeeService = new FeeServiceImpl();

        BigDecimal amount = new BigDecimal(50);
        float actual = atmFeeService.getP2pFee(amount);
        float expected = 1;

        assertEquals(expected, actual, "Failed because the expected p2p fee " + expected + " is not the " + FeeService.P2P_FEE_PERCENTAGE + "% of " + amount);
    }
}