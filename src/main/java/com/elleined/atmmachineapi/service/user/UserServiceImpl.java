package com.elleined.atmmachineapi.service.user;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.request.user.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean isExists(int userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void updateBalance(User user, BigDecimal newBalance) {
        user.setBalance(newBalance);
        userRepository.save(user);
        log.debug("User with id of {} now have new balance of {}", user.getId(), newBalance);
    }

    @Override
    public User save(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        return userRepository.save(user);
    }

    @Override
    public User getById(int userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id of " + userId + " does not exists"));
    }

    @Override
    public User getByUUID(String uuid) throws ResourceNotFoundException {
        return userRepository.fetchByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("User with UUID of " + uuid + " does not exists!"));
    }
}
