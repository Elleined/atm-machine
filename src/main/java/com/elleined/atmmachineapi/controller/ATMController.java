package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.dto.transaction.DepositTransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.PeerToPeerTransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.WithdrawTransactionDTO;
import com.elleined.atmmachineapi.mapper.TransactionMapper;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.ATMService;
import com.elleined.atmmachineapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/machine/{currentUserId}")
@RequiredArgsConstructor
public class ATMController  {
    private final ATMService atmService;

    private final TransactionMapper transactionMapper;

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/deposit")
    public DepositTransactionDTO deposit(@PathVariable("currentUserId") int currentUserId,
                                         @RequestParam("amount") BigDecimal amount) {

        DepositTransaction depositTransaction = atmService.deposit(currentUserId, amount);
        return transactionMapper.toDepositTransactionDTO(depositTransaction);
    }


    @PostMapping("/withdraw")
    public WithdrawTransactionDTO withdraw(@PathVariable("currentUserId") int currentUserId,
                                           @RequestParam("amount") BigDecimal amount) {
        WithdrawTransaction withdrawTransaction = atmService.withdraw(currentUserId, amount);
        return transactionMapper.toWithdrawTransactionDTO(withdrawTransaction);
    }


    @PostMapping("/peer-to-peer/{receiverId}")
    public PeerToPeerTransactionDTO peerToPeer(@PathVariable("currentUserId") int senderId,
                                               @RequestParam("amount") BigDecimal amount,
                                               @PathVariable("receiverId") int receiverId) {

        PeerToPeerTransaction peerToPeerTransaction = atmService.peerToPeer(senderId, amount, receiverId);
        return transactionMapper.toPeer2PeerTransactionDTO(peerToPeerTransaction);
    }
}
