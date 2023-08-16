package com.elleined.atmmachineapi;

import com.elleined.atmmachineapi.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AfterStartUp {

    private final UserService userService;

    @PostConstruct
    public void saveInitialUser() {
        if (userService.isUserExists(1)) {
            log.info("Initial user instantiation cancelled! Because initial users are already saved!");
            return;
        }
        userService.save("User 1");
        userService.save("User 2");
    }
}
