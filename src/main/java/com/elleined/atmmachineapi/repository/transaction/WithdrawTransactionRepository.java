package com.elleined.atmmachineapi.repository.transaction;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WithdrawTransactionRepository extends JpaRepository<WithdrawTransaction, Integer> {

    @Query("SELECT t FROM WithdrawTransaction t WHERE t.trn = :trn")
    WithdrawTransaction fetchByTRN(@Param("trn") String trn);

    @Query("SELECT wt FROM WithdrawTransaction wt WHERE wt.user = :currentUser")
    Page<WithdrawTransaction> findAll(@Param("currentUser") User currentUser, Pageable pageable);
}