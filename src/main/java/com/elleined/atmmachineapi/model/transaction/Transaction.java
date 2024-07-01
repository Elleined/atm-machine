package com.elleined.atmmachineapi.model.transaction;

import com.elleined.atmmachineapi.model.PrimaryKeyIdentity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class Transaction extends PrimaryKeyIdentity {

    @Column(
            name = "trn",
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
}
