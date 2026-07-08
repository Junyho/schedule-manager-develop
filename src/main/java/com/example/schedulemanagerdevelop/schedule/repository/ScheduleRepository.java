package com.example.schedulemanagerdevelop.schedule.repository;

import com.example.schedulemanagerdevelop.schedule.dto.SchedulePageResponse;
import com.example.schedulemanagerdevelop.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

        @Query("""
                    select new com.example.schedulemanagerdevelop.schedule.dto.SchedulePageResponse(
                        s.title,
                        s.content,
                        count(c.id),
                        s.createdAt,
                        s.modifiedAt,
                        u.username
                    )
                    from Schedule s
                    join s.user u
                    left join Comment c on c.schedule = s
                    group by s.id, s.title, s.content, s.createdAt, s.modifiedAt, u.username
                """)
        Page<SchedulePageResponse> findSchedulesWithCommentCount(Pageable pageable);

}
