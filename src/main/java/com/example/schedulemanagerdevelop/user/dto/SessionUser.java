package com.example.schedulemanagerdevelop.user.dto;

import lombok.Getter;

@Getter
public class SessionUser {
    private final Long id;
    private final String username;
    private final String email;

    public SessionUser(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
