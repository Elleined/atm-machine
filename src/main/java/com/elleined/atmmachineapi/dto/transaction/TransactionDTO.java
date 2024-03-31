package com.elleined.atmmachineapi.dto.transaction;

import com.elleined.atmmachineapi.dto.DTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public abstract class TransactionDTO extends DTO {
    private String trn;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public TransactionDTO(int id, String trn, BigDecimal amount, LocalDateTime transactionDate) {
        super(id);
        this.trn = trn;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
}
