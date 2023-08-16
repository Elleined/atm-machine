package com.elleined.atmmachineapi.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class Response {
    private final HttpStatus status;
    private final String message;
    private final LocalDateTime timeStamp;

    public Response(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }
}
