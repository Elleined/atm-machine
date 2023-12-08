package com.elleined.atmmachineapi.repository.transaction;

import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Integer> {
}