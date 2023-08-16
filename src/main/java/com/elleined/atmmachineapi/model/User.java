package com.elleined.atmmachineapi.model;

import com.elleined.atmmachineapi.model.transaction.DepositATMTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerATMTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawATMTransaction;
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
    List<PeerToPeerATMTransaction> sentATMTransactions;

    // recipient id reference is in peer to peer transaction table
    @OneToMany(mappedBy = "receiver")
    List<PeerToPeerATMTransaction> receiveATMTransactions;

    // user id reference is in withdraw transaction table
    @OneToMany(mappedBy = "user")
    List<WithdrawATMTransaction> withdrawTransactions;

    // user id reference is in withdraw transaction table
    @OneToMany(mappedBy = "user")
    List<DepositATMTransaction> depositTransactions;
}
