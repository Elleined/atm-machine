package com.elleined.atmmachineapi.model.transaction;

import com.elleined.atmmachineapi.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "depositTransactionCache", usage = CacheConcurrencyStrategy.READ_WRITE)


@Entity
@Table(name = "tbl_deposit_transaction")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DepositTransaction extends Transaction {

    @ManyToOne(optional = false)
    @JoinColumn(
            updatable = false,
            nullable = false,
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;
}
