package com.elleined.atmmachineapi.service.validator;

import com.elleined.atmmachineapi.model.User;

import java.math.BigDecimal;

public interface ATMValidator {
    boolean isNotValidAmount(BigDecimal amount);
}
