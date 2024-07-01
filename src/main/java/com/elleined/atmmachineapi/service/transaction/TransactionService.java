package com.elleined.atmmachineapi.service.transaction;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.transaction.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService<T extends Transaction> {
    T getById(int id) throws ResourceNotFoundException;
    static <T extends Transaction> List<T> getTransactionsByDateRange(List<T> transactions, LocalDateTime start, LocalDateTime end) {
        return transactions.stream()
                .filter(transaction -> transaction.getTransactionDate().isEqual(start)
                        || (transaction.getTransactionDate().isAfter(start) && transaction.getTransactionDate().isBefore(end)))
                .toList();
    }
}
