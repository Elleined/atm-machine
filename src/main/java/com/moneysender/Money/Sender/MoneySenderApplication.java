package com.moneysender.Money.Sender;

import com.moneysender.Money.Sender.exception.InsufficientFundException;
import com.moneysender.Money.Sender.exception.ResourceNotFoundException;
import com.moneysender.Money.Sender.service.AtmService;
import com.moneysender.Money.Sender.service.MoneySenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class MoneySenderApplication implements CommandLineRunner {

	private final AtmService atmService;
	public static void main(String[] args) {
		SpringApplication.run(MoneySenderApplication.class, args);
	}

	@Override
	public void run(String... args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your name: " );
        String name = in.nextLine();

	}
}
