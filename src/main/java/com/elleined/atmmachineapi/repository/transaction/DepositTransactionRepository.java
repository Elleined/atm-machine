package com.elleined.atmmachineapi.repository.transaction;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Integer> {

    @Query("SELECT dt FROM DepositTransaction dt WHERE dt.trn = :trn")
    DepositTransaction fetchByTRN(@Param("trn") String trn);

    @Query("SELECT dt FROM DepositTransaction dt WHERE dt.user = :currentUser")
    Page<DepositTransaction> findAll(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT dt FROM DepositTransaction dt WHERE dt.user = :currentUser AND dt.transactionDate BETWEEN :startDate AND :endDate")
    List<DepositTransaction> findAllByDateRange(@Param("currentUser") User currentUser,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);
}