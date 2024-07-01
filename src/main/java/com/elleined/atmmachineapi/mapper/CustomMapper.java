package com.elleined.atmmachineapi.mapper;

import com.elleined.atmmachineapi.model.PrimaryKeyIdentity;

public interface CustomMapper<ENTITY extends PrimaryKeyIdentity,
        DTO extends com.elleined.atmmachineapi.dto.DTO> {
    DTO toDTO(ENTITY entity);
}
