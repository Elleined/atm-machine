package com.elleined.atmmachineapi.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
