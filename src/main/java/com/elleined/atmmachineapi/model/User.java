package com.elleined.atmmachineapi.model;

import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class User extends PrimaryKeyIdentity implements UserDetails {

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
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(name = "password")
    private String password;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
