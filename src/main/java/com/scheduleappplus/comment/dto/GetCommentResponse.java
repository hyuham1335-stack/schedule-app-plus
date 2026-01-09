package com.scheduleappplus.comment.dto;

import lombok.Getter;

@Getter
public class GetCommentResponse {
    private final Long id;
    private final String content;
    private final String writer;

    public GetCommentResponse(Long id, String content, String writer) {
        this.id = id;
        this.content = content;
        this.writer = writer;
    }
}
