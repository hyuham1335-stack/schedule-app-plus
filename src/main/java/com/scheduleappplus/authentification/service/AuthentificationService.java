package com.scheduleappplus.authentification.service;

import com.scheduleappplus.authentification.dto.LoginRequest;
import com.scheduleappplus.authentification.dto.LoginResponse;
import com.scheduleappplus.authentification.exception.UnauthorizedException;
import com.scheduleappplus.user.entity.User;
import com.scheduleappplus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final UserRepository userRepository;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User findUser = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UnauthorizedException(HttpStatus.UNAUTHORIZED,"존재하지 않는 이메일입니다.")
        );

        if (!findUser.getPassword().equals(request.getPassword())) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED,"비밀번호가 틀립니다.");
        }

        return new LoginResponse(
                findUser.getId(),
                findUser.getName(),
                findUser.getEmail()
        );
    }
}
