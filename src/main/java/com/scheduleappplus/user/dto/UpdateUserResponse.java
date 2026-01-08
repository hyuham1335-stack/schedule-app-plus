package com.scheduleappplus.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateUserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    public UpdateUserResponse(Long id, String name, String email, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
