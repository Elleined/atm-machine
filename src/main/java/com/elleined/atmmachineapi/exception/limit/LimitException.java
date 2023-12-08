package com.elleined.atmmachineapi.exception.limit;

import com.elleined.atmmachineapi.exception.ATMException;

public class LimitException extends ATMException {

    public LimitException(String message) {
        super(message);
    }
}
