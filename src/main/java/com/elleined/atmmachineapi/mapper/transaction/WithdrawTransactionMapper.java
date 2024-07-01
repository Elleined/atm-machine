package com.elleined.atmmachineapi.mapper.transaction;

import com.elleined.atmmachineapi.dto.transaction.WithdrawTransactionDTO;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.math.BigDecimal;

@Mapper(
        componentModel = "spring",
        uses = {
                UserMapper.class
        }
)
public interface WithdrawTransactionMapper extends TransactionMapper<WithdrawTransaction, WithdrawTransactionDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "trn", source = "trn"),
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "transactionDate", source = "transactionDate"),
            @Mapping(target = "userDTO", source = "user")
    })
    WithdrawTransactionDTO toDTO(WithdrawTransaction withdrawTransaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "trn", expression = "java(java.util.UUID.randomUUID().toString())"),
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "transactionDate", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "user", source = "currentUser")
    })
    WithdrawTransaction toEntity(User currentUser,
                                 BigDecimal amount);
}
