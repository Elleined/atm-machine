package com.elleined.atmmachineapi.mapper.transaction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.atmmachineapi.dto.transaction.PeerToPeerTransactionDTO;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.request.transaction.PeerToPeerTransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class PeerToPeerTransactionMapperTest {

    private final PeerToPeerTransactionMapper peerToPeerTransactionMapper = Mappers.getMapper(PeerToPeerTransactionMapper.class);
    @Test
    void toDTO() {
        // Expected Value

        // Mock data
        PeerToPeerTransaction expected = PeerToPeerTransaction.builder()
                .id(1)
                .trn("TRN")
                .amount(new BigDecimal(100))
                .transactionDate(LocalDateTime.now())
                .sender(User.builder()
                        .id(1)
                        .build())
                .receiver(User.builder()
                        .id(2)
                        .build())
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        PeerToPeerTransactionDTO actual = peerToPeerTransactionMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTrn(), actual.getTrn());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getTransactionDate(), actual.getTransactionDate());
        assertEquals(expected.getSender().getId(), actual.getSenderId());
        assertEquals(expected.getReceiver().getId(), actual.getReceiverId());
    }

    @Test
    void toEntity() {
        // Expected Value

        // Mock data
        PeerToPeerTransactionRequest expected = PeerToPeerTransactionRequest.builder()
                .amount(new BigDecimal(100))
                .sender(User.builder()
                        .id(1)
                        .build())
                .receiver(User.builder()
                        .id(2)
                        .build())
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        PeerToPeerTransaction actual = peerToPeerTransactionMapper.toEntity(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getTrn());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertNotNull(actual.getTransactionDate());

        assertEquals(expected.getSender(), actual.getSender());
        assertEquals(expected.getReceiver(), actual.getReceiver());
    }
}