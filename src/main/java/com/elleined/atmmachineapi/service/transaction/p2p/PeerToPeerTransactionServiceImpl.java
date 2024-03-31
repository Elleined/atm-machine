package com.elleined.atmmachineapi.service.transaction.p2p;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.Transaction;
import com.elleined.atmmachineapi.repository.transaction.PeerToPeerTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PeerToPeerTransactionServiceImpl implements PeerToPeerTransactionService {
    private final PeerToPeerTransactionRepository peerToPeerTransactionRepository;

    @Override
    public PeerToPeerTransaction save(PeerToPeerTransaction peerToPeerTransaction) {
        peerToPeerTransactionRepository.save(peerToPeerTransaction);
        log.debug("Peer to peer transaction saved successfully with trn of {}", peerToPeerTransaction.getTrn());
        return peerToPeerTransaction;
    }

    @Override
    public PeerToPeerTransaction getById(int id) throws ResourceNotFoundException {
        return peerToPeerTransactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with id of " + id + " does't exists!"));
    }

    @Override
    public List<PeerToPeerTransaction> getAllById(Set<Integer> ids) {
        return peerToPeerTransactionRepository.findAllById(ids);
    }

    @Override
    public List<PeerToPeerTransaction> getAll(User currentUser) {
        return Stream.of(getAllSentMoneyTransactions(currentUser), getAllReceiveMoneyTransactions(currentUser))
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public List<PeerToPeerTransaction> getAllReceiveMoneyTransactions(User currentUser) {
        return currentUser.getReceiveMoneyTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .toList();
    }

    @Override
    public List<PeerToPeerTransaction> getAllSentMoneyTransactions(User currentUser) {
        return currentUser.getSentMoneyTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .toList();
    }
}
