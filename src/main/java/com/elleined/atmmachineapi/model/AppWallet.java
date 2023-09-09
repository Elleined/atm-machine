package com.elleined.atmmachineapi.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "app_wallet")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "app_wallet_id",
            nullable = false,
            updatable = false,
            unique = true
    )
    private int id;

    @Column(
            name = "app_wallet_balance",
            nullable = false
    )
    private BigDecimal balance;
}
