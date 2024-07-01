package com.elleined.atmmachineapi.model;

import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class User extends PrimaryKeyIdentity {

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "uuid",
            updatable = false,
            nullable = false,
            unique = true
    )
    private String uuid;

    @Column(
            name = "balance",
            nullable = false
    )
    private BigDecimal balance;

    @OneToMany(mappedBy = "sender")
    List<PeerToPeerTransaction> sentMoneyTransactions;

    @OneToMany(mappedBy = "receiver")
    List<PeerToPeerTransaction> receiveMoneyTransactions;

    @OneToMany(mappedBy = "user")
    List<WithdrawTransaction> withdrawTransactions;

    @OneToMany(mappedBy = "user")
    List<DepositTransaction> depositTransactions;

    public <T> boolean isBalanceNotEnough(T t) {
        return this.getBalance().compareTo(new BigDecimal(String.valueOf(t))) < 0;
    }

    public boolean isSendingToHimSelf(User receiver) {
        return this.getId() == receiver.getId() || this.equals(receiver);
    }
}
