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
public class DepositTransactionRequest extends TransactionRequest {

    private int userId;

    @Builder
    public DepositTransactionRequest(String trn, BigDecimal amount, LocalDateTime transactionDate, int userId) {
        super(trn, amount, transactionDate);
        this.userId = userId;
    }
}
