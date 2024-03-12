package com.elleined.atmmachineapi.service.fee;

import java.math.BigDecimal;

public interface FeeService {
    int DEPOSIT_FEE_PERCENTAGE = 2;
    int WITHDRAWAL_FEE_PERCENTAGE = 2;
    int P2P_FEE_PERCENTAGE = 2;

    float getWithdrawalFee(BigDecimal withdrawalAmount);
    float getDepositFee(BigDecimal depositedAmount);
    float getP2pFee(BigDecimal sentAmount);
}
