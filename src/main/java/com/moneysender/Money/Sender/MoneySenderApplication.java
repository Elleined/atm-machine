package com.moneysender.Money.Sender;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class MoneySenderApplication implements CommandLineRunner {

	private final MoneySender moneySender;

	public static void main(String[] args) {
		SpringApplication.run(MoneySenderApplication.class, args);
	}

	@Override
	public void run(String... args) {
		moneySender.doSomethnig();
	}
}
