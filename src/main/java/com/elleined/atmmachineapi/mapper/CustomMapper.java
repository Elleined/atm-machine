package com.elleined.atmmachineapi.mapper;

import com.elleined.atmmachineapi.model.PrimaryKeyIdentity;
import com.elleined.atmmachineapi.request.Request;

public interface CustomMapper<ENTITY extends PrimaryKeyIdentity, DTO extends com.elleined.atmmachineapi.dto.DTO, REQUEST extends Request> {

    DTO toDTO(ENTITY entity);

    ENTITY toEntity(REQUEST request);
}
