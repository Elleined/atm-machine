package com.elleined.atmmachineapi.mapper;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserDTO toDTO(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "depositTransactions", ignore = true),
            @Mapping(target = "receiveATMTransactions", ignore = true),
            @Mapping(target = "sentATMTransactions", ignore = true),
            @Mapping(target = "withdrawTransactions", ignore = true)
    })
    public abstract User toEntity(UserDTO userDTO);
}
