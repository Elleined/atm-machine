package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.dto.transaction.DepositTransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.PeerToPeerTransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.WithdrawTransactionDTO;
import com.elleined.atmmachineapi.mapper.transaction.DepositTransactionMapper;
import com.elleined.atmmachineapi.mapper.transaction.PeerToPeerTransactionMapper;
import com.elleined.atmmachineapi.mapper.transaction.WithdrawTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.ATMService;
import com.elleined.atmmachineapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/users/{currentUserId}")
@RequiredArgsConstructor
public class ATMController  {
    private final ATMService atmService;
    private final UserService userService;
    private final DepositTransactionMapper depositTransactionMapper;
    private final WithdrawTransactionMapper withdrawTransactionMapper;
    private final PeerToPeerTransactionMapper peerToPeerTransactionMapper;

    @PostMapping("/deposit")
    public DepositTransactionDTO deposit(@PathVariable("currentUserId") int currentUserId,
                                         @RequestParam("amount") BigDecimal amount) {

        User currentUser = userService.getById(currentUserId);
        DepositTransaction depositTransaction = atmService.deposit(currentUser, amount);
        return depositTransactionMapper.toDTO(depositTransaction);
    }


    @PostMapping("/withdraw")
    public WithdrawTransactionDTO withdraw(@PathVariable("currentUserId") int currentUserId,
                                           @RequestParam("amount") BigDecimal amount) {

        User currentUser = userService.getById(currentUserId);
        WithdrawTransaction withdrawTransaction = atmService.withdraw(currentUser, amount);
        return withdrawTransactionMapper.toDTO(withdrawTransaction);
    }


    @PostMapping("/peer-to-peer/{receiverId}")
    public PeerToPeerTransactionDTO peerToPeer(@PathVariable("currentUserId") int senderId,
                                               @RequestParam("amount") BigDecimal sentAmount,
                                               @PathVariable("receiverId") int receiverId) {

        User sender = userService.getById(senderId);
        User receiver = userService.getById(receiverId);
        PeerToPeerTransaction peerToPeerTransaction = atmService.peerToPeer(sender, receiver, sentAmount);
        return peerToPeerTransactionMapper.toDTO(peerToPeerTransaction);
    }
}
