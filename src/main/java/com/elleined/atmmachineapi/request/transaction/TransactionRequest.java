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
    private BigDecimal amount;

    public TransactionRequest(BigDecimal amount) {
        this.amount = amount;
    }
}
