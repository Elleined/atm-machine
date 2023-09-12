package com.elleined.atmmachineapi.exception.limit;

public class LimitException extends RuntimeException {

    public LimitException(String message) {
        super(message);
    }
}
