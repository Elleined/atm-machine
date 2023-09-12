package com.elleined.atmmachineapi.exception.limit;

public class LimitExceptionPerDayException extends LimitException {
    public LimitExceptionPerDayException(String message) {
        super(message);
    }
}
