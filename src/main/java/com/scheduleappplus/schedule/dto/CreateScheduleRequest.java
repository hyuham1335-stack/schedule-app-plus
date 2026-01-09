package com.scheduleappplus.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CreateScheduleRequest {

    @NotBlank
    private String title;
    @NotNull
    @Length(max = 100)
    private String content;
}
