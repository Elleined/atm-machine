package com.elleined.atmmachineapi.mapper;

import com.elleined.atmmachineapi.dto.transaction.DepositTransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.PeerToPeerTransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.TransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.WithdrawTransactionDTO;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO toTransactionDTO(Transaction transaction);

    @Mapping(target = "userId", source = "user.id")
    DepositTransactionDTO toDepositTransactionDTO(DepositTransaction depositTransaction);
    @Mapping(target = "userId", source = "user.id")
    WithdrawTransactionDTO toWithdrawTransactionDTO(WithdrawTransaction withdrawTransaction);
    @Mappings({
            @Mapping(target = "senderId", source = "sender.id"),
            @Mapping(target = "receiverId", source = "receiver.id")
    })
    PeerToPeerTransactionDTO toPeer2PeerTransactionDTO(PeerToPeerTransaction peerToPeerTransaction);
}
