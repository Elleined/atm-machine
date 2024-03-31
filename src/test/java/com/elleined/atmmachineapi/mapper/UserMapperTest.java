package com.elleined.atmmachineapi.mapper;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.request.user.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toDTO() {
        // Expected Value
        int id = 1;
        String name = "Name";
        String uuid = "UUID";
        BigDecimal balance = new BigDecimal(100);
        List<PeerToPeerTransaction> sentMoneyTransactions = new ArrayList<>();
        List<PeerToPeerTransaction> receiveMoneyTransactions = new ArrayList<>();
        List<WithdrawTransaction> withdrawTransactions = new ArrayList<>();
        List<DepositTransaction> depositTransactions = new ArrayList<>();

        // Mock data
        User expected = User.builder()
                .id(id)
                .name(name)
                .uuid(uuid)
                .balance(balance)
                .sentMoneyTransactions(sentMoneyTransactions)
                .receiveMoneyTransactions(receiveMoneyTransactions)
                .withdrawTransactions(withdrawTransactions)
                .depositTransactions(depositTransactions)
                .build();

        // Set up method


        // Stubbing methods

        // Calling the method
        UserDTO actual = userMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEquals(expected.getBalance(), actual.getBalance());
        assertEquals(expected.getSentMoneyTransactions().stream().map(Transaction::getId).toList(), actual.getSentMoneyTransactionIds());
        assertEquals(expected.getReceiveMoneyTransactions().stream().map(Transaction::getId).toList(), actual.getReceiveMoneyTransactionIds());
        assertEquals(expected.getDepositTransactions().stream().map(Transaction::getId).toList(), actual.getDepositTransactionIds());
        assertEquals(expected.getWithdrawTransactions().stream().map(Transaction::getId).toList(), actual.getWithdrawTransactionIds());
    }

    @Test
    void toEntity() {
        // Expected Value

        // Mock data
        UserRequest expected = UserRequest.builder()
                .name("Name")
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        User actual = userMapper.toEntity(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getName(), actual.getName());
        assertEquals(0, actual.getId());

        assertNotNull(actual.getUuid());
        assertNotNull(actual.getBalance());

        assertNotNull(actual.getSentMoneyTransactions());
        assertNotNull(actual.getReceiveMoneyTransactions());
        assertNotNull(actual.getWithdrawTransactions());
        assertNotNull(actual.getDepositTransactions());
    }
}