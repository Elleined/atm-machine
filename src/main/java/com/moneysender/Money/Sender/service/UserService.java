package com.moneysender.Money.Sender.service;

import com.moneysender.Money.Sender.exception.ResourceNotFoundException;
import com.moneysender.Money.Sender.model.User;
import com.moneysender.Money.Sender.repository.UserRepository;
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

    @Transactional
    public int save(String name) {
        User user = User.builder()
                .name(name)
                .balance(new BigDecimal(0))
                .build();
        userRepository.save(user);

        log.debug("User saved successfully with id of {}", user.getId());
        return user.getId();
    }

    @Transactional
    public int save(User user) {
        return userRepository.save(user).getId();
    }

    public User getById(int userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id of " + userId + " does not exists"));
    }

    public boolean isUserExists(int userId) {
        return userRepository.existsById(userId);
    }
}
