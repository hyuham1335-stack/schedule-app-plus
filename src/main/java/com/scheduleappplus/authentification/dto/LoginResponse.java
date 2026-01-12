package com.scheduleappplus.authentification.dto;

import com.scheduleappplus.authentification.domain.authentificationstatus.AuthStatus;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final Long id;
    private final String username;
    private final String message;

    public LoginResponse(Long id, String username, AuthStatus authStatus) {
        this.id = id;
        this.username = username;
        this.message = authStatus.getMessage();
    }

    public static LoginResponse alreadyLogined(SessionUser sessionUser) {
        return new LoginResponse(sessionUser.getId(), sessionUser.getUsername(), AuthStatus.ALREADY_LOGGED_IN);
    }
}
