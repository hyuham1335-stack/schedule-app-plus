package com.scheduleappplus.authentification.controller;

import com.scheduleappplus.authentification.domain.authentificationstatus.AuthStatus;
import com.scheduleappplus.authentification.dto.LoginRequest;
import com.scheduleappplus.authentification.dto.LoginResponse;
import com.scheduleappplus.authentification.dto.SessionUser;
import com.scheduleappplus.authentification.service.AuthentificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthentificationController {
    private final AuthentificationService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session,
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser
    ){
        if(loginUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(LoginResponse.alreadyLogined(loginUser));
        }


        LoginResponse loginResponse = authService.login(request);

        SessionUser sessionUser = new SessionUser(
                loginResponse.getId(),
                loginResponse.getUsername()
        );

        session.setAttribute("loginUser", sessionUser);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser,
            HttpSession session
    ){
        if(loginUser == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
