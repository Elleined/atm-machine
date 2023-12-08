package com.elleined.atmmachineapi.repository.transaction;

import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeerToPeerTransactionRepository extends JpaRepository<PeerToPeerTransaction, Integer> {
}