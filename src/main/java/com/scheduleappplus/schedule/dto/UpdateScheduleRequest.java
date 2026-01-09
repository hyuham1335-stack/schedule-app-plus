package com.scheduleappplus.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    @NotBlank
    private String title;
    @NotNull
    private String content;
}
