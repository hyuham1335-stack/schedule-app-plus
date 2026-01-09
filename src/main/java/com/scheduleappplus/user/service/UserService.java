package com.scheduleappplus.user.service;

import com.scheduleappplus.config.PasswordEncoder;
import com.scheduleappplus.user.dto.*;
import com.scheduleappplus.user.entity.User;
import com.scheduleappplus.user.exception.UserAlreadyExistsException;
import com.scheduleappplus.user.exception.UserNotFoundException;
import com.scheduleappplus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder pe;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(HttpStatus.CONFLICT ,"이미 존재하는 회원입니다.");
        }

        User newUser = new User(request.getName(), request.getEmail(), pe.encode(request.getPassword()));
        User savedUser = userRepository.save(newUser);

        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedDate(),
                savedUser.getLastModifiedDate()
        );
    }

    @Transactional(readOnly = true)
    public List<GetAllUserResponse> findAll() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .map(user -> new GetAllUserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                )).toList();
    }

    @Transactional(readOnly = true)
    public GetOneUserResponse findOne(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 유저입니다.")
        );

        return new GetOneUserResponse(
                findUser.getId(),
                findUser.getName(),
                findUser.getEmail()
        );
    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 유저입니다.")
        );

        findUser.updateUser(request.getName(), request.getEmail(), pe.encode(request.getPassword()));

        return new UpdateUserResponse(
                findUser.getId(),
                findUser.getName(),
                findUser.getEmail(),
                findUser.getCreatedDate(),
                findUser.getLastModifiedDate()
        );
    }

    @Transactional
    public void deleteUser(Long userId) {
        boolean existence = userRepository.existsById(userId);

        if (!existence) {
            throw new UserNotFoundException("존재하지 않는 유저입니다.");
        }
        userRepository.deleteById(userId);
    }
}
