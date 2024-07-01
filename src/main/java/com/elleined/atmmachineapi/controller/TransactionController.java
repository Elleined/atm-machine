package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.dto.transaction.DepositTransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.PeerToPeerTransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.WithdrawTransactionDTO;
import com.elleined.atmmachineapi.mapper.transaction.DepositTransactionMapper;
import com.elleined.atmmachineapi.mapper.transaction.PeerToPeerTransactionMapper;
import com.elleined.atmmachineapi.mapper.transaction.WithdrawTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.service.transaction.deposit.DepositTransactionService;
import com.elleined.atmmachineapi.service.transaction.p2p.PeerToPeerTransactionService;
import com.elleined.atmmachineapi.service.transaction.withdraw.WithdrawTransactionService;
import com.elleined.atmmachineapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{currentUserId}/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final UserService userService;

    private final WithdrawTransactionService withdrawTransactionService;
    private final DepositTransactionService depositTransactionService;
    private final PeerToPeerTransactionService peerToPeerTransactionService;

    private final DepositTransactionMapper depositTransactionMapper;
    private final WithdrawTransactionMapper withdrawTransactionMapper;
    private final PeerToPeerTransactionMapper peerToPeerTransactionMapper;

    @GetMapping("/withdraw")
    public List<WithdrawTransactionDTO> getAllWithdrawalTransactions(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return withdrawTransactionService.getAll(currentUser, ).stream()
                .map(withdrawTransactionMapper::toDTO)
                .toList();
    }

    @GetMapping("/deposit")
    public List<DepositTransactionDTO> getAllDepositTransactions(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return depositTransactionService.getAll(currentUser, ).stream()
                .map(depositTransactionMapper::toDTO)
                .toList();
    }

    @GetMapping("/receive-money")
    public List<PeerToPeerTransactionDTO> getAllReceiveMoneyTransactions(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return peerToPeerTransactionService.getAllReceiveMoneyTransactions(currentUser).stream()
                .map(peerToPeerTransactionMapper::toDTO)
                .toList();
    }

    @GetMapping("/sent-money")
    public List<PeerToPeerTransactionDTO> getAllSentMoneyTransactions(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return peerToPeerTransactionService.getAllSentMoneyTransactions(currentUser).stream()
                .map(peerToPeerTransactionMapper::toDTO)
                .toList();
    }
}
