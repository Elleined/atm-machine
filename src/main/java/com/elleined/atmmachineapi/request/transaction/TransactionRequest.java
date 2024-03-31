package com.elleined.atmmachineapi.request.transaction;

import com.elleined.atmmachineapi.request.Request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public abstract class TransactionRequest extends Request {
    private String trn;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public TransactionRequest(String trn, BigDecimal amount, LocalDateTime transactionDate) {
        this.trn = trn;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
}
