package com.elleined.atmmachineapi.service.atm.transaction;

import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.transaction.ATMTransaction;
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
    public ATMTransaction save(ATMTransaction atmTransaction) {
        return transactionRepository.save(atmTransaction);
    }

    @Override
    public ATMTransaction getById(int transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction with id of " + transactionId + " does not exists!"));
    }

    @Override
    public ATMTransaction getByTRN(String trn) {
        return transactionRepository.fetchByTRN(trn).orElseThrow(() -> new ResourceNotFoundException("Transaction with TRN of " + trn + " does not exists!"));
    }
}
