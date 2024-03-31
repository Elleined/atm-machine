package com.elleined.atmmachineapi.service.transaction;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.request.transaction.TransactionRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface TransactionService<T extends Transaction, U extends TransactionRequest> {

    T save(T t);
    T getById(int id) throws ResourceNotFoundException;
    List<T> getAllById(Set<Integer> ids);
    List<T> getAll(User currentUser);

    static <T extends Transaction> List<T> getTransactionsByDateRange(List<T> transactions, LocalDateTime start, LocalDateTime end) {
        return transactions.stream()
                .filter(transaction -> transaction.getTransactionDate().isEqual(start)
                        || (transaction.getTransactionDate().isAfter(start) && transaction.getTransactionDate().isBefore(end)))
                .toList();
    }
}
