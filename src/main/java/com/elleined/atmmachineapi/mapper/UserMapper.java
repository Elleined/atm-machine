package com.elleined.atmmachineapi.mapper;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.request.user.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper extends CustomMapper<User, UserDTO, UserRequest> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "uuid", source = "uuid"),
            @Mapping(target = "balance", source = "balance"),
            @Mapping(target = "sentMoneyTransactionIds", expression = "java(user.getAllSentMoneyTransactionIds())"),
            @Mapping(target = "receiveMoneyTransactionIds", expression = "java(user.getAllReceiveMoneyTransactionIds())"),
            @Mapping(target = "withdrawTransactionIds", expression = "java(user.getAllWithdrawTransactionIds())"),
            @Mapping(target = "depositTransactionIds", expression = "java(user.getAllDepositTransactionIds())"),
    })
    UserDTO toDTO(User user);

    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID().toString())"),
            @Mapping(target = "balance", expression = "java(java.math.BigDecimal.ZERO)"),
            @Mapping(target = "sentMoneyTransactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "receiveMoneyTransactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "withdrawTransactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "depositTransactions", expression = "java(new java.util.ArrayList<>())")
    })
    User toEntity(UserRequest userRequest);
}
