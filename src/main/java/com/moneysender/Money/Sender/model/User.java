package com.moneysender.Money.Sender.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private BigDecimal balance;

    // sender id reference is in transaction table
    @OneToMany(mappedBy = "sender")
    List<Transaction> sentTransactions;

    // recipient id reference is in transaction table
    @OneToMany(mappedBy = "recipient")
    List<Transaction> receiveTransactions;

    // user id reference is in withdraw transaction table
    @OneToMany(mappedBy = "user")
    List<WithdrawTransaction> withdrawTransactions;

    // user id reference is in withdraw transaction table
    @OneToMany(mappedBy = "user")
    List<DepositTransaction> depositTransactions;
}
