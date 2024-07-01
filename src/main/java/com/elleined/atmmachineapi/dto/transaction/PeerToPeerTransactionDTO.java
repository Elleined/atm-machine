package com.elleined.atmmachineapi.dto.transaction;


import com.elleined.atmmachineapi.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PeerToPeerTransactionDTO extends TransactionDTO {
    private UserDTO senderDTO;
    private UserDTO receiverDTO;

    @Builder
    public PeerToPeerTransactionDTO(int id, String trn, BigDecimal amount, LocalDateTime transactionDate, UserDTO senderDTO, UserDTO receiverDTO) {
        super(id, trn, amount, transactionDate);
        this.senderDTO = senderDTO;
        this.receiverDTO = receiverDTO;
    }
}
