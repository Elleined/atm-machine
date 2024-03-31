package com.elleined.atmmachineapi.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.antlr.v4.runtime.UnbufferedCharStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Test
    void isBalanceNotEnough() {
        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .balance(new BigDecimal(100))
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        boolean isBalanceEnough = currentUser.isBalanceNotEnough(1000);

        // Behavior Verifications

        // Assertions
        assertTrue(isBalanceEnough);
    }
}