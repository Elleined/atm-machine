package com.moneysender.Money.Sender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoneySenderServiceTest {
    private final MoneySenderService moneySenderService;

    @Autowired
    public MoneySenderServiceTest(MoneySenderService moneySenderService) {
        this.moneySenderService = moneySenderService;
    }
    
}