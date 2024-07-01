package com.elleined.atmmachineapi.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "app_wallet")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AppWallet extends PrimaryKeyIdentity {

    @Column(
            name = "app_wallet_balance",
            nullable = false
    )
    private BigDecimal balance;
}
