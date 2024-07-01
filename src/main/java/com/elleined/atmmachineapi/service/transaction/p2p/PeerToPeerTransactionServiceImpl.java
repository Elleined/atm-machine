package com.elleined.atmmachineapi.service.transaction.p2p;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.mapper.transaction.PeerToPeerTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.repository.transaction.PeerToPeerTransactionRepository;
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
public class PeerToPeerTransactionServiceImpl implements PeerToPeerTransactionService {
    private final PeerToPeerTransactionRepository peerToPeerTransactionRepository;
    private final PeerToPeerTransactionMapper peerToPeerTransactionMapper;

    @Override
    public PeerToPeerTransaction save(User sender, User receiver, BigDecimal amount) {
        PeerToPeerTransaction peerToPeerTransaction = peerToPeerTransactionMapper.toEntity(sender, receiver, amount);

        peerToPeerTransactionRepository.save(peerToPeerTransaction);
        log.debug("Peer to peer transaction saved successfully with trn of {}", peerToPeerTransaction.getTrn());
        return peerToPeerTransaction;
    }

    @Override
    public PeerToPeerTransaction getById(int id) throws ResourceNotFoundException {
        return peerToPeerTransactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with id of " + id + " does't exists!"));
    }

    @Override
    public Page<PeerToPeerTransaction> getAllReceiveMoneyTransactions(User currentUser, Pageable pageable) {
        return peerToPeerTransactionRepository.findAllReceiveMoneyTransactions(currentUser, pageable);
    }

    @Override
    public Page<PeerToPeerTransaction> getAllSentMoneyTransactions(User currentUser, Pageable pageable) {
        return peerToPeerTransactionRepository.findAllSentMoneyTransactions(currentUser, pageable);
    }
}
