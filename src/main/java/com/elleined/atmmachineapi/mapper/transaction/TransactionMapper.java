package com.elleined.atmmachineapi.mapper.transaction;

import com.elleined.atmmachineapi.dto.transaction.TransactionDTO;
import com.elleined.atmmachineapi.mapper.CustomMapper;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.request.transaction.TransactionRequest;

public interface TransactionMapper<ENTITY extends Transaction, DTO extends TransactionDTO, REQUEST extends TransactionRequest> extends CustomMapper<ENTITY, DTO, REQUEST> {
}
