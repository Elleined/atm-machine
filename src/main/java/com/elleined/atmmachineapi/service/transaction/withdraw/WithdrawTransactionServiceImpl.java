package com.elleined.atmmachineapi.service.transaction.withdraw;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.mapper.transaction.WithdrawTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.repository.transaction.WithdrawTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawTransactionServiceImpl implements WithdrawTransactionService {
    private final WithdrawTransactionRepository withdrawTransactionRepository;
    private final WithdrawTransactionMapper withdrawTransactionMapper;

    @Override
    public WithdrawTransaction getById(int id) throws ResourceNotFoundException {
        return withdrawTransactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with id of " + id + " does't exists!"));
    }

    @Override
    public Page<WithdrawTransaction> getAll(User currentUser, Pageable pageable) {
        return withdrawTransactionRepository.findAll(currentUser, pageable);
    }

    @Override
    public WithdrawTransaction save(User currentUser, BigDecimal amount) {
        WithdrawTransaction withdrawTransaction = withdrawTransactionMapper.toEntity(currentUser, amount);

        withdrawTransactionRepository.save(withdrawTransaction);
        log.debug("Saving withdraw transaction success.");
        return withdrawTransaction;
    }
}
