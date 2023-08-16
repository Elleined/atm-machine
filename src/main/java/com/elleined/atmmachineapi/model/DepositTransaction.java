package com.elleined.atmmachineapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_deposit_transaction")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_transaction_id")
    private int id;

    @Column(name = "transaction_reference_number",
            updatable = false,
            nullable = false,
            unique = true)
    private String trn;

    @Column(name = "deposit_date",
            nullable = false,
            updatable = false)
    private LocalDateTime depositDate;

    @Column(name = "deposited_amount",
            updatable = false,
            nullable = false)
    private BigDecimal amount;

    @Column(name = "account_balance",
            updatable = false,
            nullable = false)
    private BigDecimal accountBalance;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id",
            referencedColumnName = "user_id"
    )
    private User user;
}
