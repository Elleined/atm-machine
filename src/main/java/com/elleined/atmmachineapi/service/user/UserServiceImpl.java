package com.elleined.atmmachineapi.service.user;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.jwt.JWTService;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isExists(int userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public String login(String email,
                        String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        if (!authentication.isAuthenticated())
            throw new UsernameNotFoundException("Invalid credential");

        return jwtService.generateToken(email);
    }

    @Override
    public User save(String name, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = userMapper.toEntity(name, email, encodedPassword);

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
        return userRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("User with UUID of " + uuid + " does not exists!"));
    }

    @Override
    public User getByJWT(String jwt) throws ResourceNotFoundException {
        String email = jwtService.getEmail(jwt);
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email of " + email + " doesn't exists!"));
    }
}
