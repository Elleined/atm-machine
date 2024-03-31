package com.elleined.atmmachineapi.model.transaction;

import com.elleined.atmmachineapi.model.PrimaryKeyIdentity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class Transaction extends PrimaryKeyIdentity {

    @Column(
            name = "transaction_reference_number",
            updatable = false,
            nullable = false,
            unique = true
    )
    private String trn;

    @Column(
            name = "amount",
            updatable = false,
            nullable = false
    )
    private BigDecimal amount;

    @Column(
            name = "transaction_date",
            updatable = false,
            nullable = false
    )
    private LocalDateTime transactionDate;

    public Transaction(int id, String trn, BigDecimal amount, LocalDateTime transactionDate) {
        super(id);
        this.trn = trn;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
}
