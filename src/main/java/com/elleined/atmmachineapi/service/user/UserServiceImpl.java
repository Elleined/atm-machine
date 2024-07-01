package com.elleined.atmmachineapi.service.user;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean isExists(int userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User save(String name) {
        User user = userMapper.toEntity(name);

        userRepository.save(user);
        log.debug("Saving user success.");
        return user;
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
