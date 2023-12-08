package com.elleined.atmmachineapi.exception;

public class NotValidAmountException extends ATMException {
    public NotValidAmountException(String message) {
        super(message);
    }
}
