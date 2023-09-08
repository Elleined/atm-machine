package com.elleined.atmmachineapi.service.atm.transaction;

import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction save(Transaction atmTransaction) {
        return transactionRepository.save(atmTransaction);
    }

    @Override
    public Transaction getById(int transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction with id of " + transactionId + " does not exists!"));
    }

    @Override
    public Transaction getByTRN(String trn) {
        return transactionRepository.fetchByTRN(trn).orElseThrow(() -> new ResourceNotFoundException("Transaction with TRN of " + trn + " does not exists!"));
    }
}
