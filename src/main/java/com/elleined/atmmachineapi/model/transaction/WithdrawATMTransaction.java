package com.elleined.atmmachineapi.model.transaction;

import com.elleined.atmmachineapi.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_withdraw_transaction")
@NoArgsConstructor
@Getter
@Setter
public class WithdrawATMTransaction extends ATMTransaction {

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

    @Builder
    public WithdrawATMTransaction(int id, String trn, BigDecimal amount, LocalDateTime transactionDate, BigDecimal accountBalance, User user) {
        super(id, trn, amount, transactionDate);
        this.accountBalance = accountBalance;
        this.user = user;
    }
}
