package com.elleined.atmmachineapi;

import com.elleined.atmmachineapi.service.user.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class AfterStartUp {

    private final UserServiceImpl userServiceImpl;

    @PostConstruct
    public void saveInitialUser() {
        if (userServiceImpl.isUserExists(1)) {
            log.info("Initial user instantiation cancelled! Because initial users are already saved!");
            return;
        }
        userServiceImpl.save("User 1", UUID.randomUUID().toString());
        userServiceImpl.save("User 2", UUID.randomUUID().toString());
    }
}
