package com.moneysender.Money.Sender;

import com.moneysender.Money.Sender.exception.InsufficientFundException;
import com.moneysender.Money.Sender.exception.ResourceNotFoundException;
import com.moneysender.Money.Sender.service.MoneySenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class MoneySenderApplication implements CommandLineRunner {

	private final MoneySenderService moneySenderService;
	public static void main(String[] args) {
		SpringApplication.run(MoneySenderApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			moneySenderService.sendMoney(1, new BigDecimal(100), 1);
		} catch (IllegalArgumentException | ResourceNotFoundException | InsufficientFundException e) {
			log.error("Error Occurred! {}", e.getMessage());
		}
	}
}
