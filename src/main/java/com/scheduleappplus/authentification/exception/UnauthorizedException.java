package com.scheduleappplus.authentification.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AuthentificationException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
