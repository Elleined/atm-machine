package com.elleined.atmmachineapi.repository.transaction;

import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Integer> {

    @Query("SELECT t FROM DepositTransaction t WHERE t.trn = :trn")
    DepositTransaction fetchByTRN(@Param("trn") String trn);
}