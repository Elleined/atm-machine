package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.atm.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/{transactionTRN}")
    public Transaction getByTRN(@PathVariable("transactionTRN") String transactionTRN) {
        return transactionService.getByTRN(transactionTRN);
    }

    public List<WithdrawTransaction> getAllWithdrawalTransactions(User currentUser) {
        return null;
    }

    public List<DepositTransaction> getAllDepositTransactions(User currentUser) {
        return null;
    }

    public List<PeerToPeerTransaction> getAllReceiveMoneyTransactions(User currentUser) {
        return null;
    }

    public List<PeerToPeerTransaction> getAllSentMoneyTransactions(User currentUser) {
        return null;
    }
}
