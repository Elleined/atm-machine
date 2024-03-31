package com.elleined.atmmachineapi.service.transaction.withdraw;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.repository.transaction.WithdrawTransactionRepository;
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
public class WithdrawTransactionServiceImpl implements WithdrawTransactionService {
    private final WithdrawTransactionRepository withdrawTransactionRepository;

    @Override
    public WithdrawTransaction getById(int id) throws ResourceNotFoundException {
        return withdrawTransactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with id of " + id + " does't exists!"));
    }

    @Override
    public List<WithdrawTransaction> getAllById(Set<Integer> ids) {
        return withdrawTransactionRepository.findAllById(ids);
    }

    @Override
    public List<WithdrawTransaction> getAll(User currentUser) {
        return currentUser.getWithdrawTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .toList();
    }

    @Override
    public WithdrawTransaction save(WithdrawTransaction withdrawTransaction) {
        withdrawTransactionRepository.save(withdrawTransaction);
        return withdrawTransaction;
    }
}
