package com.elleined.atmmachineapi.service.machine.validator;

import java.math.BigDecimal;

public interface ATMValidator {
    boolean isNotValidAmount(BigDecimal amount);
}
