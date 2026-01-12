package com.scheduleappplus.authentification.domain.authentificationstatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthStatus {

    LOGIN_SUCCESS("Login 완료"),
    ALREADY_LOGGED_IN("이미 로그인된 상태입니다");

    private final String message;
}
