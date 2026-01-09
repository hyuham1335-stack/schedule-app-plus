package com.scheduleappplus.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CreateCommentRequest {
    @NotBlank
    @Length(max = 100)
    private String content;
}
