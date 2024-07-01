package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.dto.transaction.DepositTransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.PeerToPeerTransactionDTO;
import com.elleined.atmmachineapi.dto.transaction.WithdrawTransactionDTO;
import com.elleined.atmmachineapi.hateoas.transaction.DepositTransactionHateoasAssembler;
import com.elleined.atmmachineapi.hateoas.transaction.PeerToPeerTransactionHateoasAssembler;
import com.elleined.atmmachineapi.hateoas.transaction.WithdrawTransactionHateoasAssembler;
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

    private final WithdrawTransactionMapper withdrawTransactionMapper;
    private final WithdrawTransactionHateoasAssembler withdrawTransactionHateoasAssembler;

    private final DepositTransactionMapper depositTransactionMapper;
    private final DepositTransactionHateoasAssembler depositTransactionHateoasAssembler;

    private final PeerToPeerTransactionMapper peerToPeerTransactionMapper;
    private final PeerToPeerTransactionHateoasAssembler peerToPeerTransactionHateoasAssembler;

    @PostMapping("/deposit")
    public DepositTransactionDTO deposit(@PathVariable("currentUserId") int currentUserId,
                                         @RequestParam("amount") BigDecimal amount,
                                         @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {

        User currentUser = userService.getById(currentUserId);
        DepositTransaction depositTransaction = atmService.deposit(currentUser, amount);
        DepositTransactionDTO depositTransactionDTO = depositTransactionMapper.toDTO(depositTransaction);
        depositTransactionHateoasAssembler.addLinks(currentUser, depositTransactionDTO, includeRelatedLinks);
        return depositTransactionDTO;
    }


    @PostMapping("/withdraw")
    public WithdrawTransactionDTO withdraw(@PathVariable("currentUserId") int currentUserId,
                                           @RequestParam("amount") BigDecimal amount,
                                           @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {

        User currentUser = userService.getById(currentUserId);
        WithdrawTransaction withdrawTransaction = atmService.withdraw(currentUser, amount);
        WithdrawTransactionDTO withdrawTransactionDTO = withdrawTransactionMapper.toDTO(withdrawTransaction);
        withdrawTransactionHateoasAssembler.addLinks(currentUser, withdrawTransactionDTO, includeRelatedLinks);
        return withdrawTransactionDTO;
    }


    @PostMapping("/peer-to-peer/{receiverId}")
    public PeerToPeerTransactionDTO peerToPeer(@PathVariable("currentUserId") int senderId,
                                               @RequestParam("amount") BigDecimal sentAmount,
                                               @PathVariable("receiverId") int receiverId,
                                               @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {

        User sender = userService.getById(senderId);
        User receiver = userService.getById(receiverId);
        PeerToPeerTransaction peerToPeerTransaction = atmService.peerToPeer(sender, receiver, sentAmount);
        PeerToPeerTransactionDTO peerToPeerTransactionDTO = peerToPeerTransactionMapper.toDTO(peerToPeerTransaction);
        peerToPeerTransactionHateoasAssembler.addLinks(sender, peerToPeerTransactionDTO, includeRelatedLinks);
        return peerToPeerTransactionDTO;
    }
}
