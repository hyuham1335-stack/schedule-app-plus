package com.scheduleappplus.authentification.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AuthentificationException {
    public UnauthorizedException(HttpStatus status, String message) {
        super(status, message);
    }
}
