package com.scheduleappplus.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponse {
    private final Long id;
    private final String content;
    private final String writer;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    public UpdateCommentResponse(Long id, String content, String writer, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
