package com.elleined.atmmachineapi.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "app_wallet")
@NoArgsConstructor
@Getter
@Setter
public class AppWallet extends PrimaryKeyIdentity {

    @Column(
            name = "app_wallet_balance",
            nullable = false
    )
    private BigDecimal balance;

    @Builder
    public AppWallet(int id, BigDecimal balance) {
        super(id);
        this.balance = balance;
    }
}
