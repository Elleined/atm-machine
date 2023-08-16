package com.elleined.atmmachineapi.repository;

import com.elleined.atmmachineapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("select t from Transaction t where t.transactionNumberReference = ?1")
    Optional<Transaction> fetchByTRN(String transactionNumberReference);
}