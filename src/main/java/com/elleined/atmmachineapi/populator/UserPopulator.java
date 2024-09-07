package com.elleined.atmmachineapi.populator;

import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPopulator implements Populator {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final Faker faker;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void populate() {
        List<User> users = List.of(
                userMapper.toEntity(faker.name().fullName(), faker.bothify("##??@gmail.com"), passwordEncoder.encode("user")),
                userMapper.toEntity(faker.name().fullName(), faker.bothify("##??@gmail.com"), passwordEncoder.encode("user")),
                userMapper.toEntity(faker.name().fullName(), faker.bothify("##??@gmail.com"), passwordEncoder.encode("user")),
                userMapper.toEntity(faker.name().fullName(), faker.bothify("##??@gmail.com"), passwordEncoder.encode("user"))
        );

        userRepository.saveAll(users);
    }
}
