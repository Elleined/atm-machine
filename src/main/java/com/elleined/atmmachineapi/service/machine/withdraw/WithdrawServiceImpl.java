package com.elleined.atmmachineapi.service.machine.withdraw;


import com.elleined.atmmachineapi.exception.InsufficientFundException;
import com.elleined.atmmachineapi.exception.amount.ATMMaximumAmountException;
import com.elleined.atmmachineapi.exception.amount.ATMMinimumAmountException;
import com.elleined.atmmachineapi.exception.amount.NotValidAmountException;
import com.elleined.atmmachineapi.exception.limit.LimitExceptionPerDayException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.WithdrawTransaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.transaction.withdraw.WithdrawTransactionService;
import com.elleined.atmmachineapi.service.transaction.withdraw.WithdrawTransactionServiceImpl;
import com.elleined.atmmachineapi.service.validator.withdraw.WithdrawValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawServiceImpl implements WithdrawService {

    private final UserRepository userRepository;

    private final WithdrawTransactionService withdrawTransactionService;
    private final WithdrawValidator withdrawValidator;

    private final FeeService feeService;
    private final AppWalletService appWalletService;

    @Override
    public WithdrawTransaction withdraw(User currentUser, BigDecimal withdrawalAmount) {
        if (withdrawValidator.isNotValidAmount(withdrawalAmount))
            throw new NotValidAmountException("Cannot withdraw! because amount should be positive and cannot be zero!");

        if (currentUser.isBalanceNotEnough(withdrawalAmount))
            throw new InsufficientFundException("Cannot withdraw! because insufficient funds!");

        if (withdrawValidator.isBelowMinimum(withdrawalAmount))
            throw new ATMMinimumAmountException("Cannot withdraw! because you are trying to withdraw an amount that below to minimum amount which is " + WithdrawValidator.MINIMUM_WITHDRAW_AMOUNT);

        if (withdrawValidator.isAboveMaximum(withdrawalAmount))
            throw new ATMMaximumAmountException("Cannot withdraw! You cannot withdraw an amount that is greater than withdraw limit which is " + WithdrawValidator.MAXIMUM_WITHDRAW_AMOUNT);

        if (withdrawValidator.reachedLimitAmountPerDay(currentUser))
            throw new LimitExceptionPerDayException("Cannot withdraw! You already reached withdrawal limit per day which is " + WithdrawValidator.WITHDRAWAL_LIMIT_PER_DAY);

        BigDecimal oldBalance = currentUser.getBalance();
        float withdrawalFee = feeService.getWithdrawalFee(withdrawalAmount);
        BigDecimal finalWithdrawalAmount = withdrawalAmount.subtract(new BigDecimal(withdrawalFee));
        currentUser.setBalance(oldBalance.subtract(withdrawalAmount));

        userRepository.save(currentUser);
        appWalletService.addAndSaveBalance(withdrawalFee);
        WithdrawTransaction withdrawTransaction = withdrawTransactionService.save(currentUser, withdrawalAmount);

        log.debug("User with id of {} withdraw amounting {} from {} because of withdrawal fee of {} which is the {}% of withdrawn amount and has new balance of {} from {}", currentUser.getId(), finalWithdrawalAmount, withdrawalAmount, withdrawalFee, FeeService.WITHDRAWAL_FEE_PERCENTAGE, currentUser.getBalance(), oldBalance);
        return withdrawTransaction;
    }
}
