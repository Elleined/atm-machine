package com.moneysender.Money.Sender.repository;

import com.moneysender.Money.Sender.model.WithdrawTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawTransactionRepository extends JpaRepository<WithdrawTransaction, Integer> {
}