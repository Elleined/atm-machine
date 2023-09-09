package com.elleined.atmmachineapi.exception;

public class NotValidAmountException extends RuntimeException {
    public NotValidAmountException(String message) {
        super(message);
    }
}
