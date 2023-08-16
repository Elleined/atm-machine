package com.elleined.atmmachineapi.repository;

import com.elleined.atmmachineapi.model.WithdrawTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawTransactionRepository extends JpaRepository<WithdrawTransaction, Integer> {
}