package com.scheduleappplus.authentification.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private final Long id;
    private final String username;
    private final String email;

    public LoginResponse(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
