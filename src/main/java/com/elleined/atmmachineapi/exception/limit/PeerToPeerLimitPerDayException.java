package com.elleined.atmmachineapi.exception.limit;

public class PeerToPeerLimitPerDayException extends LimitExceptionPerDayException {
    public PeerToPeerLimitPerDayException(String message) {
        super(message);
    }
}
