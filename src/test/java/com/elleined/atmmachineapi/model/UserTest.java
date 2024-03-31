package com.elleined.atmmachineapi.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void isSenderSendingToHimself() {
        // Expected Value

        // Mock data
        User sender = User.builder()
                .id(1)
                .build();

        User receiver = User.builder()
                .id(2)
                .build();
        // Set up method

        // Stubbing methods

        // Calling the method
        boolean isSenderSendingToHimself = sender.isSendingToHimSelf(receiver);

        // Behavior Verifications

        // Assertions
        assertFalse(isSenderSendingToHimself);
    }
}