package com.elleined.atmmachineapi.repository.transaction;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PeerToPeerTransactionRepository extends JpaRepository<PeerToPeerTransaction, Integer> {

    @Query("SELECT t FROM PeerToPeerTransaction t WHERE t.trn = :trn")
    PeerToPeerTransaction fetchByTRN(@Param("trn") String trn);

    @Query("SELECT ppt FROM PeerToPeerTransaction ppt WHERE ppt.sender = :currentUser")
    Page<PeerToPeerTransaction> findAllSentMoneyTransactions(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT ppt FROM PeerToPeerTransaction ppt WHERE ppt.receiver = :currentUser")
    Page<PeerToPeerTransaction> findAllReceiveMoneyTransactions(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT ppt FROM PeerToPeerTransaction ppt WHERE ppt.sender = :currentUser AND ppt.transactionDate BETWEEN :startDate AND :endDate")
    List<PeerToPeerTransaction> findAllByDateRange(@Param("currentUser") User currentUser,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}