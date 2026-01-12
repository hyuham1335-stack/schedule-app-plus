package com.scheduleappplus.authentification.service;

import com.scheduleappplus.authentification.domain.authentificationstatus.AuthStatus;
import com.scheduleappplus.authentification.dto.LoginRequest;
import com.scheduleappplus.authentification.dto.LoginResponse;
import com.scheduleappplus.authentification.domain.exception.UnauthorizedException;
import com.scheduleappplus.config.PasswordEncoder;
import com.scheduleappplus.user.entity.User;
import com.scheduleappplus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final UserRepository userRepository;
    private final PasswordEncoder pe;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User findUser = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UnauthorizedException("존재하지 않는 이메일입니다.")
        );

        if (!pe.matches(request.getPassword(), findUser.getPassword())) {
            throw new UnauthorizedException("비밀번호가 틀립니다.");
        }

        return new LoginResponse(
                findUser.getId(),
                findUser.getName(),
                AuthStatus.LOGIN_SUCCESS
        );
    }
}
