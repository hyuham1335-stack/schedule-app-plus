package com.scheduleappplus.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetAllScheduleResponse {

    private final String title;
    private final String content;
    private final Long commentCount;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;
    private final String writer;

    public GetAllScheduleResponse(String title, String content, Long commentCount, LocalDateTime createdDate, LocalDateTime lastModifiedDate, String writer) {
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.writer = writer;
    }
}
