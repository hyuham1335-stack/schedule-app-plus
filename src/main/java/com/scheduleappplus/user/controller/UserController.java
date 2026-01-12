package com.scheduleappplus.user.controller;

import com.scheduleappplus.authentification.domain.exception.UnauthorizedException;
import com.scheduleappplus.authentification.dto.SessionUser;
import com.scheduleappplus.user.dto.*;
import com.scheduleappplus.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping("users")
    public ResponseEntity<List<GetAllUserResponse>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUser(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    @PatchMapping("users/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser
    ) {
        if(loginUser == null){
            throw new UnauthorizedException("로그인이 필요합니다");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, request));
    }

    @DeleteMapping("users/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId,
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser,
            HttpSession session
    ){
        if(loginUser == null){
            throw new UnauthorizedException("로그인이 필요합니다");
        }

        userService.deleteUser(userId);
        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
