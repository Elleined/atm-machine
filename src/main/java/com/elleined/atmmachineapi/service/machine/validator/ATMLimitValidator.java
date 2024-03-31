package com.elleined.atmmachineapi.service.machine.validator;

import java.math.BigDecimal;

public interface ATMLimitValidator {
    boolean isBelowMinimum(BigDecimal amount);
    boolean isAboveMaximum(BigDecimal amount);
}