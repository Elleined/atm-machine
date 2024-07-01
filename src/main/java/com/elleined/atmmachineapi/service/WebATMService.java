package com.elleined.atmmachineapi.service;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.machine.deposit.DepositService;
import com.elleined.atmmachineapi.service.machine.p2p.PeerToPeerService;
import com.elleined.atmmachineapi.service.machine.withdraw.WithdrawService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Primary
public class WebATMService implements ATMService {

    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final PeerToPeerService peerToPeerService;

    @Override
    public DepositTransaction deposit(User currentUser, BigDecimal depositedAmount) {
        return depositService.deposit(currentUser, depositedAmount);
    }

    @Override
    public WithdrawTransaction withdraw(User currentUser, BigDecimal withdrawnAmount) {
        return withdrawService.withdraw(currentUser, withdrawnAmount);
    }

    @Override
    public PeerToPeerTransaction peerToPeer(User sender, User receiver, BigDecimal sentAmount) {
        return peerToPeerService.peerToPeer(sender, receiver, sentAmount);
    }
}
