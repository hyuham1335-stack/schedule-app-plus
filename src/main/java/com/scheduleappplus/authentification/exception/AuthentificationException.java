package com.scheduleappplus.authentification.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthentificationException extends RuntimeException {
    private final HttpStatus status;
    public AuthentificationException(HttpStatus status ,String message) {
        super(message);
        this.status = status;
    }
}
