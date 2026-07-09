package com.example.schedulemanagerdevelop.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupUser {
    @NotBlank(message = "유저명은 필수입니다.")
    @Size(max = 10 , message = "유저명은 10글자 이내")
    private String username;
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
    @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하여야 합니다.")
    private String password;
}
