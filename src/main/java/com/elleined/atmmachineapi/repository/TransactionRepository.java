package com.elleined.atmmachineapi.repository;

import com.elleined.atmmachineapi.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.trn = :trn")
    Optional<Transaction> fetchByTRN(@Param("trn") String trn);


}