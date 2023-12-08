package com.elleined.atmmachineapi.service;

import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.NotValidAmountException;
import com.elleined.atmmachineapi.exception.SendingToHimselfException;
import com.elleined.atmmachineapi.exception.limit.LimitException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.atm.DepositService;
import com.elleined.atmmachineapi.service.atm.PeerToPeerService;
import com.elleined.atmmachineapi.service.atm.WithdrawService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@Primary
public class WebATMService implements ATMService {

    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final PeerToPeerService peerToPeerService;

    @Override
    public DepositTransaction deposit(User currentUser, @NonNull  BigDecimal depositedAmount)
            throws NotValidAmountException, LimitException {
        return depositService.deposit(currentUser, depositedAmount);
    }

    @Override
    public WithdrawTransaction withdraw(User currentUser, @NonNull  BigDecimal withdrawnAmount)
            throws InsufficientFundException, NotValidAmountException, LimitException {
        return withdrawService.withdraw(currentUser, withdrawnAmount);
    }

    @Override
    public PeerToPeerTransaction peerToPeer(User sender, User receiver, @NonNull  BigDecimal sentAmount)
            throws SendingToHimselfException, InsufficientFundException, NotValidAmountException, LimitException {
        return peerToPeerService.peerToPeer(sender, receiver, sentAmount);
    }
}
