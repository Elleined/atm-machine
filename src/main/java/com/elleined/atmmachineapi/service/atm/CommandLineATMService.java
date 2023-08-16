package com.elleined.atmmachineapi.service.atm;

import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Qualifier("commandLineAtm")
public class CommandLineATMService implements ATMService, Runnable {

    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final PeerToPeerService peerToPeerService;
    private final UserService userService;

    @Override
    public void deposit(int userId, @NonNull BigDecimal amount) {
        BigDecimal newBalance = depositService.deposit(userId, amount);
        System.out.println("You successfully deposited amounting " + amount);
        System.out.println("=== Your new Account balance is " + newBalance + " ===");
    }

    @Override
    public void withdraw(int userId, @NonNull BigDecimal amount) {
        BigDecimal newBalance = withdrawService.withdraw(userId, amount);
        System.out.println("You successfully withdrawn amounting " + amount);
        System.out.println("=== Your new Account balance is " + newBalance + " ===");
    }

    @Override
    public void peerToPeer(int senderId, @NonNull BigDecimal amount, int receiverId) {
        BigDecimal senderNewBalance = peerToPeerService.peerToPeer(senderId, amount, receiverId);
        System.out.println("You successfully send money to the recipient with id of " + receiverId + " amounting " + amount);
        System.out.println("=== Your new Account balance is " + senderNewBalance + " ===");
    }

    @Override
    public void run() {
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
                    this.withdraw(userId, in.nextBigDecimal());

                    System.out.print("Do you want to make another transaction? (Y/N): ");
                    response = yerOrNo.nextLine().charAt(0);
                    if (response == 'N' || response == 'n') response = 'N';
                }
                case 2 -> {
                    System.out.println("=== Account Balance: " + user.getBalance() + " ===");
                    System.out.print("Enter amount you want to deposit: ");
                    this.deposit(userId, in.nextBigDecimal());

                    System.out.print("Do you want to make another transaction? (Y/N): ");
                    response = yerOrNo.nextLine().charAt(0);
                    if (response == 'N' || response == 'n') response = 'N';
                }
                case 3 -> {
                    System.out.println("=== Account Balance: " + user.getBalance() + " ===");
                    System.out.print("Enter the recipient's id: " );
                    int receiverId = in.nextInt();
                    if (!userService.isUserExists(receiverId)) {
                        log.error("User with id of {} does not exists!", receiverId);
                        return;
                    }

                    System.out.print("Enter amount to be sent: ");
                    BigDecimal amount = in.nextBigDecimal();
                    log.trace("Sender id: {}. Recipient id: {}. Amount to be sent: {}", userId, amount, receiverId);
                    this.peerToPeer(userId, amount, receiverId);

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
