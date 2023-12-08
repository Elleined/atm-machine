package com.elleined.atmmachineapi.exception;

public class InsufficientFundException extends ATMException {
    public InsufficientFundException(String message) {
        super(message);
    }
}
