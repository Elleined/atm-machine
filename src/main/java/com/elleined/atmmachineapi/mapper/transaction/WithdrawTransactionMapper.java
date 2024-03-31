package com.elleined.atmmachineapi.mapper.transaction;

import com.elleined.atmmachineapi.dto.transaction.WithdrawTransactionDTO;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.request.transaction.WithdrawTransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WithdrawTransactionMapper extends TransactionMapper<WithdrawTransaction, WithdrawTransactionDTO, WithdrawTransactionRequest> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "trn", source = "trn"),
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "transactionDate", source = "transactionDate"),
            @Mapping(target = "userId", source = "user.id")
    })
    WithdrawTransactionDTO toDTO(WithdrawTransaction withdrawTransaction);

    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "trn", expression = "java(java.util.UUID.randomUUID().toString())"),
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "transactionDate", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "user", source = "user")
    })
    WithdrawTransaction toEntity(WithdrawTransactionRequest request);
}
