package com.scheduleappplus.user.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends UserException {
    public UserAlreadyExistsException(HttpStatus status, String message) {
        super(status, message);
    }
}
