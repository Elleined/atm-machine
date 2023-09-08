package com.elleined.atmmachineapi.mapper;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "depositTransactions", ignore = true),
            @Mapping(target = "receiveMoneyTransactions", ignore = true),
            @Mapping(target = "sentMoneyTransactions", ignore = true),
            @Mapping(target = "withdrawTransactions", ignore = true)
    })
    User toEntity(UserDTO userDTO);
}
