package com.elleined.atmmachineapi;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.service.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
        UserDTO user1 = UserDTO.builder()
                .name("User 1")
                .uuid(UUID.randomUUID().toString())
                .build();

        UserDTO user2 = UserDTO.builder()
                .name("User 2")
                .uuid(UUID.randomUUID().toString())
                .build();

        userService.save(user1);
        userService.save(user2);
    }
}
