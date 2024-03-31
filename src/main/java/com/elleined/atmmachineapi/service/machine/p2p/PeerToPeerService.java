package com.elleined.atmmachineapi.service.machine.p2p;

import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.SendingToHimselfException;
import com.elleined.atmmachineapi.exception.amount.NotValidAmountException;
import com.elleined.atmmachineapi.exception.limit.LimitException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import lombok.NonNull;

import java.math.BigDecimal;

public interface PeerToPeerService {
    PeerToPeerTransaction peerToPeer(User sender, User receiver, @NonNull BigDecimal sentAmount)
            throws SendingToHimselfException,
            InsufficientFundException,
            NotValidAmountException,
            LimitException;
}
