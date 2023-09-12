package com.elleined.atmmachineapi.exception.limit;

public class DepositLimitPerDayException extends LimitExceptionPerDayException {
    public DepositLimitPerDayException(String message) {
        super(message);
    }
}
