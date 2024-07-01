package com.elleined.atmmachineapi.service.transaction.deposit;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.mapper.transaction.DepositTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.repository.transaction.DepositTransactionRepository;
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
public class DepositTransactionServiceImpl implements DepositTransactionService {
    private final DepositTransactionRepository depositTransactionRepository;
    private final DepositTransactionMapper depositTransactionMapper;

    @Override
    public DepositTransaction getById(int id) throws ResourceNotFoundException {
        return depositTransactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with id of " + id + " does't exists!"));
    }

    @Override
    public Page<DepositTransaction> getAll(User currentUser, Pageable pageable) {
        return depositTransactionRepository.findAll(currentUser, pageable);
    }

    @Override
    public DepositTransaction save(User currentUser, BigDecimal amount) {
        DepositTransaction depositTransaction = depositTransactionMapper.toEntity(currentUser, amount);

        depositTransactionRepository.save(depositTransaction);
        log.debug("Saving deposit transaction success.");
        return depositTransaction;
    }
}
