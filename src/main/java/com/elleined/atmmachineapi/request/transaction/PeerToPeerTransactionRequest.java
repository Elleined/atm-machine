package com.elleined.atmmachineapi.request.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PeerToPeerTransactionRequest extends TransactionRequest {

    private int senderId;
    private int receiverId;

    @Builder
    public PeerToPeerTransactionRequest(String trn, BigDecimal amount, LocalDateTime transactionDate, int senderId, int receiverId) {
        super(trn, amount, transactionDate);
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
