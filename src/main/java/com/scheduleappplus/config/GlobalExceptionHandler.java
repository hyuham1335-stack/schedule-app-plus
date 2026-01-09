package com.scheduleappplus.config;

import com.scheduleappplus.authentification.exception.AuthentificationException;
import com.scheduleappplus.authentification.exception.UnauthorizedException;
import com.scheduleappplus.schedule.exception.ScheduleException;
import com.scheduleappplus.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthentificationException.class)
    public ResponseEntity<String> handleAuthentificationException(AuthentificationException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(ScheduleException.class)
    public ResponseEntity<String> handleScheduleException(ScheduleException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }
}
