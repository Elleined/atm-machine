package com.elleined.atmmachineapi.exception.amount;

public class DepositAmountBelowMinimumException extends ATMAmountException {
    public DepositAmountBelowMinimumException(String message) {
        super(message);
    }
}
