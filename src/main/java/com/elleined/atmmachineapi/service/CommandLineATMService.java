package com.elleined.atmmachineapi.service;

import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.model.transaction.PeerToPeerTransaction;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.atm.DepositService;
import com.elleined.atmmachineapi.service.atm.PeerToPeerService;
import com.elleined.atmmachineapi.service.atm.WithdrawService;
import com.elleined.atmmachineapi.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("commandLineAtm")
public class CommandLineATMService implements ATMService, Runnable {

    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final PeerToPeerService peerToPeerService;
    private final UserServiceImpl userServiceImpl;

    @Override
    public DepositTransaction deposit(int userId, @NonNull BigDecimal amount) {
        try {
            DepositTransaction depositTransaction = depositService.deposit(userId, amount);
            System.out.println("You successfully deposited amounting " + amount);
            System.out.println("=== Your new Account balance is " + depositTransaction.getUser().getBalance() + " ===");
        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            log.error("Error Occurred! {}", e.getMessage());
        }
        return null;
    }

    @Override
    public WithdrawTransaction withdraw(int userId, @NonNull BigDecimal amount) {
        try {
            WithdrawTransaction withdrawTransaction = withdrawService.withdraw(userId, amount);
            System.out.println("You successfully withdrawn amounting " + amount);
            System.out.println("=== Your new Account balance is " + withdrawTransaction.getUser().getBalance() + " ===");
        } catch (IllegalArgumentException | ResourceNotFoundException | InsufficientFundException e) {
            log.error("Error Occurred! {}", e.getMessage());
        }
        return null;
    }

    @Override
    public PeerToPeerTransaction peerToPeer(int senderId, @NonNull BigDecimal amount, int receiverId) {
        try {
            PeerToPeerTransaction peerToPeerTransaction = peerToPeerService.peerToPeer(senderId, amount, receiverId);
            System.out.println("You successfully send money to the recipient with id of " + receiverId + " amounting " + amount);
            System.out.println("=== Your new Account balance is " + peerToPeerTransaction.getSender().getBalance() + " ===");
        } catch (IllegalArgumentException | ResourceNotFoundException | InsufficientFundException e) {
            log.error("Error Occurred! {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void run() {
        final Scanner in = new Scanner(System.in);
        System.out.print("Enter your user id: ");
        int userId = in.nextInt();
        if (!userServiceImpl.isUserExists(userId)) {
            log.error("User with id of {} does not exists!", userId);
            return;
        }

        String name = userServiceImpl.getById(userId).getName();
        System.out.println("=== Welcome " + name + " ====");

        final Scanner yerOrNo = new Scanner(System.in);
        char response = 'Y';
        do {
            User user = userServiceImpl.getById(userId);

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
                    if (!userServiceImpl.isUserExists(receiverId)) {
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
