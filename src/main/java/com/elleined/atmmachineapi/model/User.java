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

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tbl_user")
@NoArgsConstructor
@Getter
@Setter
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

    // sender id reference is in peer to peer transaction table
    @OneToMany(mappedBy = "sender")
    List<PeerToPeerTransaction> sentMoneyTransactions;

    // recipient id reference is in peer to peer transaction table
    @OneToMany(mappedBy = "receiver")
    List<PeerToPeerTransaction> receiveMoneyTransactions;

    // user id reference is in withdraw transaction table
    @OneToMany(mappedBy = "user")
    List<WithdrawTransaction> withdrawTransactions;

    // user id reference is in withdraw transaction table
    @OneToMany(mappedBy = "user")
    List<DepositTransaction> depositTransactions;

    @Builder
    public User(int id,
                String name,
                String uuid,
                BigDecimal balance,
                List<PeerToPeerTransaction> sentMoneyTransactions,
                List<PeerToPeerTransaction> receiveMoneyTransactions,
                List<WithdrawTransaction> withdrawTransactions,
                List<DepositTransaction> depositTransactions) {
        super(id);
        this.name = name;
        this.uuid = uuid;
        this.balance = balance;
        this.sentMoneyTransactions = sentMoneyTransactions;
        this.receiveMoneyTransactions = receiveMoneyTransactions;
        this.withdrawTransactions = withdrawTransactions;
        this.depositTransactions = depositTransactions;
    }

    public <T> boolean isBalanceNotEnough(T t) {
        return this.getBalance().compareTo(new BigDecimal(String.valueOf(t))) < 0;
    }

    public boolean isSendingToHimSelf(User receiver) {
        return this.getId() == receiver.getId() || this.equals(receiver);
    }

    public List<Integer> getAllSentMoneyTransactionIds() {
        return this.getSentMoneyTransactions().stream()
                .map(Transaction::getId)
                .toList();
    }

    public List<Integer> getAllReceiveMoneyTransactionIds() {
        return this.getReceiveMoneyTransactions().stream()
                .map(Transaction::getId)
                .toList();
    }

    public List<Integer> getAllWithdrawTransactionIds() {
        return this.getWithdrawTransactions().stream()
                .map(Transaction::getId)
                .toList();
    }

    public List<Integer> getAllDepositTransactionIds() {
        return this.getDepositTransactions().stream()
                .map(Transaction::getId)
                .toList();
    }


}
