package com.elleined.atmmachineapi.service.fee;

import com.elleined.atmmachineapi.model.User;

import java.math.BigDecimal;

public interface FeeService {
    int DEPOSIT_FEE_PERCENTAGE = 2;
    int WITHDRAWAL_FEE_PERCENTAGE = 2;
    int P2P_FEE_PERCENTAGE = 2;

    float getWithdrawalFee(BigDecimal withdrawalAmount);
    float getDepositFee(BigDecimal depositedAmount);
    float getP2pFee(BigDecimal sentAmount);

    void deductDepositFee(User currentUser, BigDecimal depositedAmount);

    void deductWithdrawalFee(User currentUser, BigDecimal withdrawnAmount);

    void deductP2pFee(User sender, User receiver, BigDecimal sentAmount);
}
