package com.example.schedulemanagerdevelop.auth.controller;

import com.example.schedulemanagerdevelop.auth.dto.LoginUser;
import com.example.schedulemanagerdevelop.auth.dto.SessionUser;
import com.example.schedulemanagerdevelop.auth.dto.SignupUser;
import com.example.schedulemanagerdevelop.user.dto.UserResponse;
import com.example.schedulemanagerdevelop.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupUser signupUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(signupUser));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginUser loginUser,
            HttpSession session
    ) {
        SessionUser sessionUser = userService.login(loginUser);
        session.setAttribute("loginUser",sessionUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session
    ) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
