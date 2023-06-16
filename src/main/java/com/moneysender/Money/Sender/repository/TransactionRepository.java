package com.moneysender.Money.Sender.repository;

import com.moneysender.Money.Sender.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}