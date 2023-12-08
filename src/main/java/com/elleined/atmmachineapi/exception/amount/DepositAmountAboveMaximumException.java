package com.elleined.atmmachineapi.exception.amount;

public class DepositAmountAboveMaximumException extends ATMAmountException {
    public DepositAmountAboveMaximumException(String message) {
        super(message);
    }
}
