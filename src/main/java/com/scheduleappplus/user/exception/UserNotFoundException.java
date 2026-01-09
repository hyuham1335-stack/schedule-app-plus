package com.scheduleappplus.user.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
}
