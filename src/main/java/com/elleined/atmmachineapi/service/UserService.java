package com.elleined.atmmachineapi.service;

import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void save(String name, String uuid) {
        User user = User.builder()
                .name(name)
                .balance(new BigDecimal(0))
                .uuid(uuid)
                .build();

        userRepository.save(user);
        log.debug("User saved successfully with id of {}", user.getId());
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getById(int userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id of " + userId + " does not exists"));
    }

    public boolean isUserExists(int userId) {
        return userRepository.existsById(userId);
    }
}
