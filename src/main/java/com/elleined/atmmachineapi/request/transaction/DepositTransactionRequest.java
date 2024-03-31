package com.elleined.atmmachineapi.request.transaction;

import com.elleined.atmmachineapi.model.User;
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

    private User user;

    @Builder
    public DepositTransactionRequest(BigDecimal amount, User user) {
        super(amount);
        this.user = user;
    }
}
