package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.model.transaction.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/users/{userId}/transactions")
public class TransactionController {

    @GetMapping("/{transactionTRN}")
    public Set<Transaction> getByTRN(@PathVariable("transactionTRN") String transactionTRN) {
        return null;
    }
}
