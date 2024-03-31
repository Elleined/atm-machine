package com.elleined.atmmachineapi.exception.amount;

public class NotValidAmountException extends ATMAmountException {
    public NotValidAmountException(String message) {
        super(message);
    }
}
