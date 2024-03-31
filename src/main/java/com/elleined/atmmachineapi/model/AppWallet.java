package com.elleined.atmmachineapi.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
