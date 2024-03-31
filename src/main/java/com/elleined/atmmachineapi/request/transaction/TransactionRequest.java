package com.elleined.atmmachineapi.request.transaction;

import com.elleined.atmmachineapi.request.Request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public abstract class TransactionRequest extends Request {
    private BigDecimal amount;

    public TransactionRequest(BigDecimal amount) {
        this.amount = amount;
    }
}
