package com.elleined.atmmachineapi;

import com.elleined.atmmachineapi.populator.UserPopulator;
import com.elleined.atmmachineapi.populator.WalletPopulator;
import com.elleined.atmmachineapi.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AfterStartUp {

    private final UserRepository userRepository;

    private final UserPopulator userPopulator;
    private final WalletPopulator walletPopulator;

    @PostConstruct
    public void init() {
        if (userRepository.existsById(1)) {
            log.info("Initial user instantiation cancelled! Because initial users are already saved!");
            return;
        }
        log.debug("Saving initial users and app wallet Please wait...");
        userPopulator.populate();
        walletPopulator.populate();
        log.debug("Saving initial users and app wallet successful... Thanks");
    }
}

