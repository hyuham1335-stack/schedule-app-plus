package com.scheduleappplus.authentification.domain.exception;

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
