package com.elleined.atmmachineapi.model;

import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
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

    @Column(
            name = "uuid",
            updatable = false,
            nullable = false,
            unique = true
    )
    private String uuid;

    @Column(name = "balance")
    private BigDecimal balance;

    // sender id reference is in peer to peer transaction table
    @OneToMany(mappedBy = "sender")
    List<PeerToPeerTransaction> sentATMTransactions;

    // recipient id reference is in peer to peer transaction table
    @OneToMany(mappedBy = "receiver")
    List<PeerToPeerTransaction> receiveATMTransactions;

    // user id reference is in withdraw transaction table
    @OneToMany(mappedBy = "user")
    List<WithdrawTransaction> withdrawTransactions;

    // user id reference is in withdraw transaction table
    @OneToMany(mappedBy = "user")
    List<DepositTransaction> depositTransactions;
}
