package com.elleined.atmmachineapi.mapper;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper extends CustomMapper<User, UserDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "uuid", source = "uuid"),
            @Mapping(target = "balance", source = "balance")
    })
    UserDTO toDTO(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "name", expression = "java(name)"),
            @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID().toString())"),
            @Mapping(target = "balance", expression = "java(BigDecimal.ZERO)"),
            @Mapping(target = "sentMoneyTransactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "receiveMoneyTransactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "withdrawTransactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "depositTransactions", expression = "java(new java.util.ArrayList<>())")
    })
    User toEntity(String name);
}
