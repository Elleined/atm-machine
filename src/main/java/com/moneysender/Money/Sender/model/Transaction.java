package com.moneysender.Money.Sender.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_money_transaction", indexes = @Index(name = "trn_idx", columnList = "trn"))
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @Column(name = "amount",
            updatable = false,
            nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date",
            updatable = false,
            nullable = false)
    private LocalDateTime transactionDate;


    @Column(name = "trn",
            nullable = false,
            unique = true,
            updatable = false)
    private String transactionNumberReference;

    @ManyToOne
    @JoinColumn(
            updatable = false,
            nullable = false,
            name = "sender_id",
            referencedColumnName = "user_id"
    )
    private User sender;

    @ManyToOne
    @JoinColumn(
            updatable = false,
            nullable = false,
            name = "recipient_id",
            referencedColumnName = "user_id"
    )
    private User recipient;
}
