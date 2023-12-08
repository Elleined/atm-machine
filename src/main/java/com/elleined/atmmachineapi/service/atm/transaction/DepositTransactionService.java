package com.elleined.atmmachineapi.service.atm.transaction;

import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.repository.transaction.DepositTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DepositTransactionService implements TransactionService<DepositTransaction> {
    private final DepositTransactionRepository depositTransactionRepository;

    @Override
    public DepositTransaction getById(int id) throws ResourceNotFoundException {
        return depositTransactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with id of " + id + " does't exists!"));
    }

    @Override
    public List<DepositTransaction> getAllById(Set<Integer> ids) {
        return depositTransactionRepository.findAllById(ids);
    }

    @Override
    public List<DepositTransaction> getAll(User currentUser) {
        return currentUser.getDepositTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .toList();
    }

    @Override
    public DepositTransaction save(User user, BigDecimal depositedAmount) {
        DepositTransaction depositTransaction = DepositTransaction.builder()
                .trn(UUID.randomUUID().toString())
                .amount(depositedAmount)
                .transactionDate(LocalDateTime.now())
                .user(user)
                .build();
        depositTransactionRepository.save(depositTransaction);

        return depositTransaction;
    }
}
