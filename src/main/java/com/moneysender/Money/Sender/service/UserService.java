package com.moneysender.Money.Sender.service;

import com.moneysender.Money.Sender.model.User;
import com.moneysender.Money.Sender.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public int save() {
        RandomNameGenerator rand = new RandomNameGenerator(0);
        String name = rand.next();
        User user = User.builder()
                .name(name)
                .balance(new BigDecimal(0))
                .build();
        return userRepository.save(user).getId();
    }

    public int save(String name) {
        User user = User.builder()
                .name(name)
                .balance(new BigDecimal(0))
                .build();
        return userRepository.save(user).getId();
    }
}
