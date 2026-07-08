package com.example.schedulemanagerdevelop.schedule.repository;

import com.example.schedulemanagerdevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
