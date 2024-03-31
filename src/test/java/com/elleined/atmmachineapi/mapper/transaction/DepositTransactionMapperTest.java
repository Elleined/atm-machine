package com.elleined.atmmachineapi.mapper.transaction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.dto.transaction.DepositTransactionDTO;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.request.transaction.DepositTransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class DepositTransactionMapperTest {

    private final DepositTransactionMapper depositTransactionMapper = Mappers.getMapper(DepositTransactionMapper.class);
    @Test
    void toDTO() {
        // Expected Value

        // Mock data
        DepositTransaction expected = DepositTransaction.builder()
                .id(1)
                .trn("TRN")
                .amount(new BigDecimal(100))
                .transactionDate(LocalDateTime.now())
                .user(User.builder()
                        .id(1)
                        .build())
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        DepositTransactionDTO actual = depositTransactionMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTrn(), actual.getTrn());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getTransactionDate(), actual.getTransactionDate());
        assertEquals(expected.getUser().getId(), actual.getUserId());
    }

    @Test
    void toEntity() {
        // Expected Value

        // Mock data
        DepositTransactionRequest expected = DepositTransactionRequest.builder()
                .amount(new BigDecimal(100))
                .user(new User())
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        DepositTransaction actual = depositTransactionMapper.toEntity(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getTrn());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertNotNull(actual.getTransactionDate());
        assertEquals(expected.getUser(), actual.getUser());

    }
}