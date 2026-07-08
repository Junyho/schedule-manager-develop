package com.example.schedulemanagerdevelop.comment.controller;

import com.example.schedulemanagerdevelop.comment.dto.CommentRequest;
import com.example.schedulemanagerdevelop.comment.dto.CommentResponse;
import com.example.schedulemanagerdevelop.comment.service.CommentService;
import com.example.schedulemanagerdevelop.common.exception.UnauthorizedException;
import com.example.schedulemanagerdevelop.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentResponse> create(@PathVariable Long scheduleId,
                                                  @RequestBody CommentRequest request,
                                                  @SessionAttribute(required = false) SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(scheduleId, request, sessionUser.getId()));
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponse>> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(commentService.findByScheduleId(scheduleId));
    }

}
