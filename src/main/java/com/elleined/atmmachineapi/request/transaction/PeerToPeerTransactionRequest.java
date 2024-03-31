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
public class PeerToPeerTransactionRequest extends TransactionRequest {

    private User sender;
    private User receiver;

    @Builder
    public PeerToPeerTransactionRequest(BigDecimal amount, User sender, User receiver) {
        super(amount);
        this.sender = sender;
        this.receiver = receiver;
    }
}
