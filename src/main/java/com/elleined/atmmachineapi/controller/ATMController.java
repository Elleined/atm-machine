package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.service.ATMService;
import com.elleined.atmmachineapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/atmMachine/{currentUserId}")
@RequiredArgsConstructor
public class ATMController  {
    private final ATMService atmService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/deposit")
    public UserDTO deposit(@PathVariable("currentUserId") int currentUserId,
                           @RequestParam("amount") BigDecimal amount) {
        atmService.deposit(currentUserId, amount);
        return userMapper.toDTO( userService.getById(currentUserId) );
    }


    @PostMapping("/withdraw")
    public UserDTO withdraw(@PathVariable("currentUserId") int currentUserId,
                         @RequestParam("amount") BigDecimal amount) {
        atmService.withdraw(currentUserId, amount);
        return userMapper.toDTO( userService.getById(currentUserId) );
    }


    @PostMapping("/peerToPeer/{receiverId}")
    public UserDTO peerToPeer(@PathVariable("currentUserId") int senderId,
                           @RequestParam("amount") BigDecimal amount,
                           @PathVariable("receiverId") int receiverId) {
        atmService.peerToPeer(senderId, amount, receiverId);
        return userMapper.toDTO( userService.getById(senderId) );
    }
}
