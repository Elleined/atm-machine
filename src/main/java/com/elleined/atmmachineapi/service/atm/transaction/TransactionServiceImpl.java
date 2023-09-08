package com.elleined.atmmachineapi.service.atm.transaction;

import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Transaction getByTRN(String trn) {
        return transactionRepository.fetchByTRN(trn).orElseThrow(() -> new ResourceNotFoundException("Transaction with TRN of " + trn + " does not exists!"));
    }

    @Override
    public List<WithdrawTransaction> getAllWithdrawalTransactions(User currentUser) {
        return currentUser.getWithdrawTransactions();
    }

    @Override
    public List<DepositTransaction> getAllDepositTransactions(User currentUser) {
        return currentUser.getDepositTransactions();
    }

    @Override
    public List<PeerToPeerTransaction> getAllReceiveMoneyTransactions(User currentUser) {
        return currentUser.getReceiveMoneyTransactions();
    }

    @Override
    public List<PeerToPeerTransaction> getAllSentMoneyTransactions(User currentUser) {
        return currentUser.getSentMoneyTransactions();
    }
}
