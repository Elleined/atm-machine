package com.elleined.atmmachineapi.repository;

import com.elleined.atmmachineapi.model.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Integer> {
}