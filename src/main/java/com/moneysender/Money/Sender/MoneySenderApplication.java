package com.moneysender.Money.Sender;

import com.moneysender.Money.Sender.service.MoneySenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
@RequiredArgsConstructor
public class MoneySenderApplication implements CommandLineRunner {

	private final MoneySenderService moneySenderService;
	public static void main(String[] args) {
		SpringApplication.run(MoneySenderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		moneySenderService.sendMoney(1, new BigDecimal(100), 2);
	}
}
