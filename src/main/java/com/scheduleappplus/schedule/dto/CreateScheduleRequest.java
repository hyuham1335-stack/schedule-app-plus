package com.scheduleappplus.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    private String writer;
    private String title;
    private String content;
}
