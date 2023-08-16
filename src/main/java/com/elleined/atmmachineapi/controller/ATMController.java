package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.service.atm.ATMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/atmMachine/{currentUserId}")
@RequiredArgsConstructor
public class ATMController  {
    private final ATMService atmService;

    @PostMapping("/deposit")
    public void deposit(@PathVariable("currentUserId") int currentUserId,
                        @RequestParam("amount") BigDecimal amount) {
        atmService.deposit(currentUserId, amount);
    }


    @PostMapping("/withdraw")
    public void withdraw(@PathVariable("currentUserId") int currentUserId,
                         @RequestParam("amount") BigDecimal amount) {
        atmService.withdraw(currentUserId, amount);
    }


    @PostMapping("/peerToPeer/{receiverId}")
    public void peerToPeer(@PathVariable("currentUserId") int senderId,
                           @RequestParam("amount") BigDecimal amount,
                           @PathVariable("receiverId") int receiverId) {
        atmService.peerToPeer(senderId, amount, receiverId);
    }
}
