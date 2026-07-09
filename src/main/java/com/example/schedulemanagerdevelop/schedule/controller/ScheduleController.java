package com.example.schedulemanagerdevelop.schedule.controller;

import com.example.schedulemanagerdevelop.common.exception.UnauthorizedException;
import com.example.schedulemanagerdevelop.schedule.dto.CreateSchedule;
import com.example.schedulemanagerdevelop.schedule.dto.SchedulePageResponse;
import com.example.schedulemanagerdevelop.schedule.dto.ScheduleResponse;
import com.example.schedulemanagerdevelop.schedule.dto.UpdateSchedule;
import com.example.schedulemanagerdevelop.schedule.service.ScheduleService;
import com.example.schedulemanagerdevelop.auth.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(
            @Valid @RequestBody CreateSchedule createSchedule,
            @SessionAttribute(name = "loginUser", required = false)SessionUser sessionUser
    ){
        validateLogin(sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(sessionUser.getId(), createSchedule));
    }

    @GetMapping
    public ResponseEntity<Page<SchedulePageResponse>> findSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(scheduleService.findSchedules(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(
            @PathVariable Long id,
            @SessionAttribute(name = "loginUser", required = false)SessionUser sessionUser,
            @Valid @RequestBody UpdateSchedule updateSchedule
    ) {
        validateLogin(sessionUser);
        return ResponseEntity.ok(scheduleService.update(id, sessionUser.getId(), updateSchedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @SessionAttribute(name = "loginUser", required = false)SessionUser sessionUser
    ) {
        validateLogin(sessionUser);
        scheduleService.delete(id,sessionUser.getId());
        return ResponseEntity.noContent().build();
    }

    private void validateLogin(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
    }
}
