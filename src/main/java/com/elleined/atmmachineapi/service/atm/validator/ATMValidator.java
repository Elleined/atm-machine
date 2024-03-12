package com.elleined.atmmachineapi.service.atm.validator;

import java.math.BigDecimal;

public interface ATMValidator {
    boolean isNotValidAmount(BigDecimal amount);
}
