package com.scheduleappplus.schedule.dto;

import lombok.Getter;

@Getter
public class GetAllScheduleResponse {

    private final Long scheduleId;
    private final String writer;
    private final String title;
    private final String content;

    public GetAllScheduleResponse(Long scheduleId, String writer, String title, String content) {
        this.scheduleId = scheduleId;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
