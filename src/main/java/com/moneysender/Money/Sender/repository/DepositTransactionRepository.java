package com.moneysender.Money.Sender.repository;

import com.moneysender.Money.Sender.model.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Integer> {
}