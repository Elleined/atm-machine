package com.elleined.atmmachineapi.service.machine.deposit;

import com.elleined.atmmachineapi.exception.amount.ATMMaximumAmountException;
import com.elleined.atmmachineapi.exception.amount.ATMMinimumAmountException;
import com.elleined.atmmachineapi.exception.amount.NotValidAmountException;
import com.elleined.atmmachineapi.exception.limit.LimitExceptionPerDayException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.transaction.deposit.DepositTransactionService;
import com.elleined.atmmachineapi.service.validator.deposit.DepositValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {
    private final UserRepository userRepository;

    private final DepositTransactionService depositTransactionService;
    private final DepositValidator depositValidator;

    private final FeeService feeService;

    private final AppWalletService appWalletService;

    @Override
    public DepositTransaction deposit(User currentUser, BigDecimal depositedAmount) {
        if (depositValidator.isNotValidAmount(depositedAmount))
            throw new NotValidAmountException("Cannot deposit! because amount should be positive and cannot be zero!");

        if (depositValidator.isBelowMinimum(depositedAmount))
            throw new ATMMaximumAmountException("Cannot deposit! because you are trying to deposit an amount that is below minimum which is " + DepositValidator.MINIMUM_DEPOSIT_AMOUNT);

        if (depositValidator.isAboveMaximum(depositedAmount))
            throw new ATMMinimumAmountException("Cannot deposit! because you are trying to deposit an amount that is greater than to deposit limit which is " + DepositValidator.DEPOSIT_LIMIT_PER_DAY);

        if (depositValidator.reachedLimitAmountPerDay(currentUser, depositedAmount))
            throw new LimitExceptionPerDayException("Cannot deposit! because you've already reached limit per day!");

        BigDecimal oldBalance = currentUser.getBalance();
        float depositFee = feeService.getDepositFee(depositedAmount);
        BigDecimal finalDepositedAmount = depositedAmount.subtract(new BigDecimal(depositFee));
        currentUser.setBalance(oldBalance.add(finalDepositedAmount));

        userRepository.save(currentUser);
        appWalletService.addAndSaveBalance(depositFee);
        DepositTransaction depositTransaction = depositTransactionService.save(currentUser, depositedAmount);

        log.debug("User with id of {} deposited amounting {} from {} because of deposit fee of {} which is the {}% of the deposited amount and now has new balance of {} from {}", currentUser.getId(), finalDepositedAmount, depositedAmount, depositFee, FeeService.DEPOSIT_FEE_PERCENTAGE, currentUser.getBalance(), oldBalance);
        return depositTransaction;
    }
}
