package com.elleined.atmmachineapi.service.atm;


import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.NotValidAmountException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.service.atm.transaction.TransactionService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WithdrawService {
    private final UserService userService;

    private final ATMValidator atmValidator;

    private final TransactionService transactionService;

    private final FeeService feeService;


    public WithdrawTransaction withdraw(User currentUser, @NonNull BigDecimal withdrawalAmount)
            throws InsufficientFundException,
            NotValidAmountException {

        if (atmValidator.isValidAmount(withdrawalAmount)) throw new NotValidAmountException("Amount should be positive and cannot be zero!");
        if (atmValidator.isBalanceEnough(currentUser, withdrawalAmount)) throw new InsufficientFundException("Insufficient Funds!");

        BigDecimal oldBalance = currentUser.getBalance();
        currentUser.setBalance(oldBalance.subtract(withdrawalAmount));
        userService.save(currentUser);
        feeService.deductWithdrawalFee(currentUser, withdrawalAmount);

        WithdrawTransaction withdrawTransaction = saveWithdrawTransaction(currentUser, withdrawalAmount);
        log.debug("User with id of {} withdraw amounting {} and has new balance of {} from {}", currentUser.getId(), withdrawalAmount, currentUser.getBalance(), oldBalance);
        return withdrawTransaction;
    }

    private WithdrawTransaction saveWithdrawTransaction(@NonNull User user, @NonNull BigDecimal withdrawalAmount) {
        String trn = UUID.randomUUID().toString();

        float withdrawalFee = feeService.getWithdrawalFee(withdrawalAmount);
        BigDecimal finalWithdrawalAmount = withdrawalAmount.subtract(new BigDecimal(withdrawalFee));
        WithdrawTransaction withdrawTransaction = WithdrawTransaction.builder()
                .trn(trn)
                .amount(finalWithdrawalAmount)
                .transactionDate(LocalDateTime.now())
                .user(user)
                .build();

        transactionService.save(withdrawTransaction);
        log.debug("Deposit transaction saved with trn of {}", trn);
        return withdrawTransaction;
    }
}
