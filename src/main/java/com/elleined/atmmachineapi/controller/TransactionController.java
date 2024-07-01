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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{currentUserId}")
@RequiredArgsConstructor
public class TransactionController {
    private final UserService userService;

    private final WithdrawTransactionService withdrawTransactionService;
    private final WithdrawTransactionMapper withdrawTransactionMapper;

    private final DepositTransactionService depositTransactionService;
    private final DepositTransactionMapper depositTransactionMapper;

    private final PeerToPeerTransactionService peerToPeerTransactionService;
    private final PeerToPeerTransactionMapper peerToPeerTransactionMapper;

    @GetMapping("/withdraw-transactions")
    public Page<WithdrawTransactionDTO> getAllWithdrawalTransactions(@PathVariable("currentUserId") int currentUserId,
                                                                     @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                                     @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                                     @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                                     @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy,
                                                                     @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return withdrawTransactionService.getAll(currentUser, pageable)
                .map(withdrawTransactionMapper::toDTO)
                .map(dto -> dto.addLinks(currentUser, includeRelatedLinks));
    }

    @GetMapping("/deposit-transactions")
    public Page<DepositTransactionDTO> getAllDepositTransactions(@PathVariable("currentUserId") int currentUserId,
                                                                 @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                                 @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                                 @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                                 @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy,
                                                                 @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {
        User currentUser = userService.getById(currentUserId);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);
        return depositTransactionService.getAll(currentUser, pageable)
                .map(depositTransactionMapper::toDTO)
                .map(dto -> dto.addLinks(currentUser, includeRelatedLinks));
    }

    @GetMapping("/receive-money-transactions")
    public Page<PeerToPeerTransactionDTO> getAllReceiveMoneyTransactions(@PathVariable("currentUserId") int currentUserId,
                                                                         @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                                         @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                                         @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                                         @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy,
                                                                         @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {
        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return peerToPeerTransactionService.getAllReceiveMoneyTransactions(currentUser, pageable)
                .map(peerToPeerTransactionMapper::toDTO)
                .map(dto -> dto.addLinks(currentUser, includeRelatedLinks));
    }

    @GetMapping("/sent-money-transactions")
    public Page<PeerToPeerTransactionDTO> getAllSentMoneyTransactions(@PathVariable("currentUserId") int currentUserId,
                                                                      @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                                      @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                                      @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                                      @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy,
                                                                      @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return peerToPeerTransactionService.getAllReceiveMoneyTransactions(currentUser, pageable)
                .map(peerToPeerTransactionMapper::toDTO)
                .map(dto -> dto.addLinks(currentUser, includeRelatedLinks));
    }
}
