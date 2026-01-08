package com.scheduleappplus.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {
    private final Long scheduleId;
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    public UpdateScheduleResponse(Long scheduleId, String writer, String title, String content, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.scheduleId = scheduleId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
