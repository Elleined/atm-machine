package com.elleined.atmmachineapi.exception.limit;

public class WithdrawLimitPerDayException extends LimitExceptionPerDayException {
    public WithdrawLimitPerDayException(String message) {
        super(message);
    }
}
