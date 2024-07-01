package com.elleined.atmmachineapi.mapper.transaction;

import com.elleined.atmmachineapi.dto.transaction.TransactionDTO;
import com.elleined.atmmachineapi.mapper.CustomMapper;
import com.elleined.atmmachineapi.model.transaction.Transaction;

public interface TransactionMapper<ENTITY extends Transaction,
        DTO extends TransactionDTO> extends CustomMapper<ENTITY, DTO> {
}
