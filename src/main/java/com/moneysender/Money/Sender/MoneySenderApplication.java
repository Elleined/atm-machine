package com.moneysender.Money.Sender;

import com.moneysender.Money.Sender.model.User;
import com.moneysender.Money.Sender.service.AtmService;
import com.moneysender.Money.Sender.service.UserService;
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
	private final UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(MoneySenderApplication.class, args);
	}

	@Override
	public void run(String... args) {
        final Scanner in = new Scanner(System.in);
		System.out.print("Enter you user id: ");
		int userId = in.nextInt();
		if (!userService.isUserExists(userId)) {
			log.error("User with id of {} does not exists!", userId);
			return;
		}

		String name = userService.getById(userId).getName();
		System.out.println("=== Welcome " + name + " ====");

		final Scanner yerOrNo = new Scanner(System.in);
		char response = 'Y';
        do {
			User user = userService.getById(userId);

			System.out.print("""
				1. Withdraw
				2. Deposit
				3. Send Money
				Choose your action:\s""");
			int action = in.nextInt();
			switch (action) {
				case 1 -> {
					System.out.println("=== Account Balance: " + user.getBalance() + " ===");
					System.out.print("Enter amount you want to withdraw: ");
					atmService.withdraw(userId, in.nextBigDecimal());

					System.out.print("Do you want to make another transaction? (Y/N): ");
					response = yerOrNo.nextLine().charAt(0);
					if (response == 'N' || response == 'n') response = 'N';
				}
				case 2 -> {
					System.out.println("=== Account Balance: " + user.getBalance() + " ===");
					System.out.print("Enter amount you want to deposit: ");
					atmService.deposit(userId, in.nextBigDecimal());

					System.out.print("Do you want to make another transaction? (Y/N): ");
					response = yerOrNo.nextLine().charAt(0);
					if (response == 'N' || response == 'n') response = 'N';
				}
				case 3 -> {
					System.out.println("=== Account Balance: " + user.getBalance() + " ===");
					System.out.print("Enter the recipient's id: " );
					int recipientId = in.nextInt();
					if (!userService.isUserExists(recipientId)) {
						log.error("User with id of {} does not exists!", recipientId);
						return;
					}

					System.out.print("Enter amount to be sent: ");
					BigDecimal amount = in.nextBigDecimal();
					log.trace("Sender id: {}. Recipient id: {}. Amount to be sent: {}", userId, amount, recipientId);
					atmService.sendMoney(userId, amount, recipientId);

					System.out.print("Do you want to make another transaction? (Y/N): ");
					response = yerOrNo.nextLine().charAt(0);
					if (response == 'N' || response == 'n') response = 'N';
				}
				default -> log.error("Please choose only within the specified choices!");
			}
		} while (response != 'N');

		System.out.println("=== Thank you for using my atm application :) ===");
	}
}
