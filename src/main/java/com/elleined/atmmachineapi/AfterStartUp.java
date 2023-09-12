package com.elleined.atmmachineapi;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.model.AppWallet;
import com.elleined.atmmachineapi.repository.AppWalletRepository;
import com.elleined.atmmachineapi.service.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class AfterStartUp {

    private final AppWalletRepository appWalletRepository;
    private final UserService userService;

    @PostConstruct
    public void init() {
        if (userService.isUserExists(1)) {
            log.info("Initial user instantiation cancelled! Because initial users are already saved!");
            return;
        }
        log.debug("Saving initial users and app wallet Please wait...");
        UserDTO user1 = UserDTO.builder()
                .name("Sample user 1")
                .uuid(UUID.randomUUID().toString())
                .balance(new BigDecimal(100))
                .build();

        UserDTO user2 = UserDTO.builder()
                .name("Sample user 2")
                .uuid(UUID.randomUUID().toString())
                .balance(new BigDecimal(100))
                .build();

        userService.save(user1);
        userService.save(user2);

        AppWallet appWallet = AppWallet.builder()
                .balance(new BigDecimal(0))
                .build();
        appWalletRepository.save(appWallet);

        log.debug("Saving initial users and app wallet successful... Thanks");
    }
}
