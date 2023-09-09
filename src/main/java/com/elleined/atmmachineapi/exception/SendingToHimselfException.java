package com.elleined.atmmachineapi.exception;

public class SendingToHimselfException extends RuntimeException {
    public SendingToHimselfException(String message) {
        super(message);
    }
}
