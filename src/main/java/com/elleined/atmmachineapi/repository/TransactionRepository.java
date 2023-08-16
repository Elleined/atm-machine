package com.elleined.atmmachineapi.repository;

import com.elleined.atmmachineapi.model.transaction.ATMTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<ATMTransaction, Integer> {
    @Query("SELECT t FROM ATMTransaction t WHERE t.trn = :trn")
    Optional<ATMTransaction> fetchByTRN(@Param("trn") String trn);
}