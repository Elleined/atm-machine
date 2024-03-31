package com.elleined.atmmachineapi.service.machine.deposit;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.mapper.transaction.DepositTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.repository.transaction.DepositTransactionRepository;
import com.elleined.atmmachineapi.request.transaction.DepositTransactionRequest;
import com.elleined.atmmachineapi.service.machine.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DepositTransactionService implements TransactionService<DepositTransaction, DepositTransactionRequest> {
    private final DepositTransactionRepository depositTransactionRepository;
    private final DepositTransactionMapper depositTransactionMapper;

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
    public DepositTransaction save(DepositTransactionRequest depositTransactionRequest) {
        DepositTransaction depositTransaction = depositTransactionMapper.toEntity(depositTransactionRequest);
        depositTransactionRepository.save(depositTransaction);
        return depositTransaction;
    }
}
