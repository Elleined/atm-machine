package com.moneysender.Money.Sender;

import com.moneysender.Money.Sender.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MoneySender {
    private final UserService userService;


}
