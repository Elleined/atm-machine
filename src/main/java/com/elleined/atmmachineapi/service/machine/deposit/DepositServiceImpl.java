package com.elleined.atmmachineapi.service.machine.deposit;

import com.elleined.atmmachineapi.exception.amount.ATMMaximumAmountException;
import com.elleined.atmmachineapi.exception.amount.ATMMinimumAmountException;
import com.elleined.atmmachineapi.exception.amount.NotValidAmountException;
import com.elleined.atmmachineapi.exception.limit.LimitException;
import com.elleined.atmmachineapi.mapper.transaction.DepositTransactionMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.model.transaction.DepositTransaction;
import com.elleined.atmmachineapi.repository.UserRepository;
import com.elleined.atmmachineapi.request.transaction.DepositTransactionRequest;
import com.elleined.atmmachineapi.service.AppWalletService;
import com.elleined.atmmachineapi.service.fee.FeeService;
import com.elleined.atmmachineapi.service.transaction.deposit.DepositTransactionService;
import com.elleined.atmmachineapi.service.validator.deposit.DepositValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DepositServiceImpl implements DepositService {
    private final UserRepository userRepository;

    private final DepositTransactionService depositTransactionService;
    private final DepositTransactionMapper depositTransactionMapper;
    private final DepositValidator depositValidator;

    private final FeeService feeService;

    private final AppWalletService appWalletService;


    @Override
    public DepositTransaction deposit(User currentUser, @NonNull BigDecimal depositedAmount)
            throws NotValidAmountException, LimitException {

        if (depositValidator.isNotValidAmount(depositedAmount)) throw new NotValidAmountException("Amount should be positive and cannot be zero!");
        if (depositValidator.isBelowMinimum(depositedAmount)) throw new ATMMaximumAmountException("Cannot deposit! because you are trying to deposit an amount that is below minimum which is " + DepositValidator.MINIMUM_DEPOSIT_AMOUNT);
        if (depositValidator.isAboveMaximum(depositedAmount)) throw new ATMMinimumAmountException("You cannot deposit an amount that is greater than to deposit limit which is " + DepositValidator.DEPOSIT_LIMIT_PER_DAY);

        BigDecimal oldBalance = currentUser.getBalance();
        float depositFee = feeService.getDepositFee(depositedAmount);
        BigDecimal finalDepositedAmount = depositedAmount.subtract(new BigDecimal(depositFee));
        currentUser.setBalance(oldBalance.add(finalDepositedAmount));
        userRepository.save(currentUser);
        appWalletService.addAndSaveBalance(depositFee);

        DepositTransactionRequest depositTransactionRequest = DepositTransactionRequest.builder()
                .amount(depositedAmount)
                .user(currentUser)
                .build();

        DepositTransaction depositTransaction = depositTransactionMapper.toEntity(depositTransactionRequest);
        depositTransactionService.save(depositTransaction);
        log.debug("User with id of {} deposited amounting {} from {} because of deposit fee of {} which is the {}% of the deposited amount and now has new balance of {} from {}", currentUser.getId(), finalDepositedAmount, depositedAmount, depositFee, FeeService.DEPOSIT_FEE_PERCENTAGE, currentUser.getBalance(), oldBalance);
        return depositTransaction;
    }
}
