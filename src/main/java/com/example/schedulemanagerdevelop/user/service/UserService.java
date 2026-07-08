package com.example.schedulemanagerdevelop.user.service;

import com.example.schedulemanagerdevelop.common.exception.NotFoundException;
import com.example.schedulemanagerdevelop.common.exception.UnauthorizedException;
import com.example.schedulemanagerdevelop.config.PasswordEncoder;
import com.example.schedulemanagerdevelop.user.dto.*;
import com.example.schedulemanagerdevelop.user.entity.User;
import com.example.schedulemanagerdevelop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse create(SignupUser signupUser) {
        String encodedPassword = passwordEncoder.encode(signupUser.getPassword());
        User user = new User(
                signupUser.getUsername(),
                signupUser.getEmail(),
                encodedPassword
        );
        User savedUser = userRepository.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    @Transactional
    public SessionUser login(LoginUser loginUser) {
        //login 유저를 email로 찾고
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(
                () -> new NotFoundException("없는 유저입니다.")
        );

        //password 검사 하고`
        if (!passwordEncoder.matches(loginUser.getPassword(),user.getPassword())) {
            throw new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        //session user 반환
        return new SessionUser(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<UserResponse> responseList = new ArrayList<>();
        List<User> userList = userRepository.findAll();

        for (User user : userList) {
            UserResponse response = new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            );
            responseList.add(response);
        }
        return responseList;
    }

    @Transactional
    public UserResponse findById(Long id) {
        User user = getElseThrow(id);
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public UserResponse update(Long id, UpdateUser updateUser) {
        User user = getElseThrow(id);

        user.update(
                updateUser.getUsername(),
                updateUser.getEmail(),
                updateUser.getPassword()
        );

        userRepository.flush();

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long id) {
        User user = getElseThrow(id);
        userRepository.deleteById(id);
    }

    private User getElseThrow(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("없는 유저입니다.")
        );
    }


}
