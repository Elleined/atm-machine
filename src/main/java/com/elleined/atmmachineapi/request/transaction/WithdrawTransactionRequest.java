package com.elleined.atmmachineapi.request.transaction;

import com.elleined.atmmachineapi.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class WithdrawTransactionRequest extends TransactionRequest {
    private User user;

    @Builder
    public WithdrawTransactionRequest(BigDecimal amount, User user) {
        super(amount);
        this.user = user;
    }
}
