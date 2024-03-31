package com.elleined.atmmachineapi.repository.transaction;

import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PeerToPeerTransactionRepository extends JpaRepository<PeerToPeerTransaction, Integer> {

    @Query("SELECT t FROM PeerToPeerTransaction t WHERE t.trn = :trn")
    PeerToPeerTransaction fetchByTRN(@Param("trn") String trn);
}