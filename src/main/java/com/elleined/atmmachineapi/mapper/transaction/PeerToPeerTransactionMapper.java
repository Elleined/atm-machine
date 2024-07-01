package com.elleined.atmmachineapi.mapper.transaction;

import com.elleined.atmmachineapi.dto.transaction.PeerToPeerTransactionDTO;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
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
public interface PeerToPeerTransactionMapper extends TransactionMapper<PeerToPeerTransaction, PeerToPeerTransactionDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "trn", source = "trn"),
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "transactionDate", source = "transactionDate"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "senderDTO", source = "sender")
    })
    PeerToPeerTransactionDTO toDTO(PeerToPeerTransaction peerToPeerTransaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "trn", expression = "java(java.util.UUID.randomUUID().toString())"),
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "transactionDate", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "receiver", source = "receiver"),
            @Mapping(target = "sender", source = "sender")
    })
    PeerToPeerTransaction toEntity(User sender,
                                   User receiver,
                                   BigDecimal amount);
}
