package com.example.schedulemanagerdevelop.schedule.service;

import com.example.schedulemanagerdevelop.common.exception.ForbiddenException;
import com.example.schedulemanagerdevelop.common.exception.NotFoundException;
import com.example.schedulemanagerdevelop.schedule.dto.CreateSchedule;
import com.example.schedulemanagerdevelop.schedule.dto.SchedulePageResponse;
import com.example.schedulemanagerdevelop.schedule.dto.ScheduleResponse;
import com.example.schedulemanagerdevelop.schedule.dto.UpdateSchedule;
import com.example.schedulemanagerdevelop.schedule.entity.Schedule;
import com.example.schedulemanagerdevelop.schedule.repository.ScheduleRepository;
import com.example.schedulemanagerdevelop.user.entity.User;
import com.example.schedulemanagerdevelop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleResponse create(Long userId, CreateSchedule createSchedule) {

        User user = getUserOrThrow(userId);

        Schedule schedule = new Schedule(
                createSchedule.getTitle(),
                createSchedule.getContent(),
                user
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                user.getId(),
                user.getUsername(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<SchedulePageResponse> findSchedules(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "modifiedAt")
        );

        return scheduleRepository.findSchedulesWithCommentCount(pageable);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAll() {
        List<ScheduleResponse> responseList = new ArrayList<>();

        List<Schedule> scheduleList =  scheduleRepository.findAll();
        for (Schedule schedule : scheduleList) {
            User user = schedule.getUser();
            ScheduleResponse response = new ScheduleResponse(
              schedule.getId(),
              schedule.getTitle(),
              schedule.getContent(),
                    user.getId(),
                    user.getUsername(),
              schedule.getCreatedAt(),
              schedule.getModifiedAt()
            );
            responseList.add(response);
        }
        return responseList;
    }

    @Transactional(readOnly = true)
    public ScheduleResponse findById(Long id) {
        Schedule schedule = getScheduleOfThrow(id);
        User user = schedule.getUser();
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                user.getId(),
                user.getUsername(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public ScheduleResponse update(Long id, Long userId, UpdateSchedule updateSchedule) {
        Schedule schedule = getScheduleOfThrow(id);

        if (!schedule.getUser().getId().equals(userId)) {
            throw new ForbiddenException("본인이 작성한 일정만 수정할 수 있습니다.");
        }

        User user = schedule.getUser();

        schedule.update(updateSchedule.getTitle(), updateSchedule.getContent());


        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                user.getId(),
                user.getUsername(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Schedule schedule = getScheduleOfThrow(id);

        if (!schedule.getUser().getId().equals(userId)) {
            throw new ForbiddenException("본인이 작성한 일정만 삭제할 수 있습니다.");
        }

        scheduleRepository.delete(schedule);
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("없는 유저입니다.")
        );
    }

    private Schedule getScheduleOfThrow(Long id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new NotFoundException("없는 일정입니다.")
        );
    }


}
