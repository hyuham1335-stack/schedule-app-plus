package com.scheduleappplus.comment.exception;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends CommentException {
    public CommentNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
