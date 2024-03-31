package com.elleined.atmmachineapi.service.validator;

import java.math.BigDecimal;

public interface ATMValidator {
    default boolean isNotValidAmount(BigDecimal amount) {
        return amount == null || amount.compareTo(BigDecimal.ZERO) <= 0;
    }
}
