package com.scheduleappplus.schedule.exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ScheduleException {
    public ScheduleNotFoundException(HttpStatus status ,String message) {
        super(status, message);
    }
}
