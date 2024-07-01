package com.elleined.atmmachineapi.service.machine.p2p;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;

import java.math.BigDecimal;

public interface PeerToPeerService {
    PeerToPeerTransaction peerToPeer(User sender, User receiver, BigDecimal sentAmount);
}
