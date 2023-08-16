package com.elleined.atmmachineapi.service.user;

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
public class UserServiceImpl implements UserService {

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

    public boolean isUserExists(int userId) {
        return userRepository.existsById(userId);
    }

    public BigDecimal getBalance(int id) throws ResourceNotFoundException {
        return getById(id).getBalance();
    }

    public BigDecimal getBalance(String uuid) throws ResourceNotFoundException {
        return getByUUID(uuid).getBalance();
    }

    @Override
    public User getById(int userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id of " + userId + " does not exists"));
    }

    @Override
    public User getByUUID(String uuid) throws ResourceNotFoundException {
        return userRepository.fetchByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("User with UUID of " + uuid + " does not exists!"));
    }

    @Override
    public int getIdByUUID(String uuid) throws ResourceNotFoundException {
        return getByUUID(uuid).getId();
    }

    @Override
    public String getUUIDById(int id) throws ResourceNotFoundException {
        return getById(id).getUuid();
    }
}
