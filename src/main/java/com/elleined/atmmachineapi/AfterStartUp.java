package com.elleined.atmmachineapi;

import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.AppWallet;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.repository.AppWalletRepository;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.request.user.UserRequest;
import com.elleined.atmmachineapi.service.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class AfterStartUp {

    private final AppWalletRepository appWalletRepository;

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostConstruct
    public void init() {
        if (userService.isExists(1)) {
            log.info("Initial user instantiation cancelled! Because initial users are already saved!");
            return;
        }
        log.debug("Saving initial users and app wallet Please wait...");

        UserRequest userRequest1 = UserRequest.builder()
                .name("Sample User 1")
                .build();

        UserRequest userRequest2 = UserRequest.builder()
                .name("Sample User 1")
                .build();

        User user1 = userMapper.toEntity(userRequest1);
        User user2 = userMapper.toEntity(userRequest2);

        userRepository.save(user1);
        userRepository.save(user2);

        AppWallet appWallet = AppWallet.builder()
                .balance(new BigDecimal(0))
                .build();
        appWalletRepository.save(appWallet);

        log.debug("Saving initial users and app wallet successful... Thanks");
    }
}

