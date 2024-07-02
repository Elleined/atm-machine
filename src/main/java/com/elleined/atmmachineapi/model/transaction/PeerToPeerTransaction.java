package com.elleined.atmmachineapi.model.transaction;

import com.elleined.atmmachineapi.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "peerToPeerTransactionCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_peer_to_peer_transaction")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PeerToPeerTransaction extends Transaction {

    @ManyToOne(optional = false)
    @JoinColumn(
            updatable = false,
            nullable = false,
            name = "sender_id",
            referencedColumnName = "id"
    )
    private User sender;

    @ManyToOne(optional = false)
    @JoinColumn(
            updatable = false,
            nullable = false,
            name = "receiver_id",
            referencedColumnName = "id"
    )
    private User receiver;
}
