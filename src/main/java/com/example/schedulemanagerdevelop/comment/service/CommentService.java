package com.example.schedulemanagerdevelop.comment.service;

import com.example.schedulemanagerdevelop.comment.dto.CommentRequest;
import com.example.schedulemanagerdevelop.comment.dto.CommentResponse;
import com.example.schedulemanagerdevelop.comment.entity.Comment;
import com.example.schedulemanagerdevelop.comment.repository.CommentRepository;
import com.example.schedulemanagerdevelop.common.exception.NotFoundException;
import com.example.schedulemanagerdevelop.schedule.entity.Schedule;
import com.example.schedulemanagerdevelop.schedule.repository.ScheduleRepository;
import com.example.schedulemanagerdevelop.user.entity.User;
import com.example.schedulemanagerdevelop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse create(Long scheduleId, CommentRequest request, Long userId) {
        Schedule schedule = getScheduleOrThrow(scheduleId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저가 없습니다.")
        );

        Comment comment = new Comment(request.getContent(),schedule,user);
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getUser().getUsername(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    private Schedule getScheduleOrThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("일정이 없습니다.")
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findByScheduleId(Long scheduleId) {
        List<CommentResponse> commentResponseList = new ArrayList<>();
        getScheduleOrThrow(scheduleId);

        List<Comment> commentList = commentRepository.findByScheduleId(scheduleId);
        for (Comment comment : commentList) {
            CommentResponse commentResponse = new CommentResponse(
                    comment.getId(),
                    comment.getContent(),
                    comment.getUser().getUsername(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            commentResponseList.add(commentResponse);
        }
        return commentResponseList;
    }


}
