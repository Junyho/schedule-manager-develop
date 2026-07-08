package com.example.schedulemanagerdevelop.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulePageResponse {
    private final String title;
    private final String content;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String username;

    public SchedulePageResponse(String title, String content, Long commentCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String username) {
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.username = username;
    }
}
