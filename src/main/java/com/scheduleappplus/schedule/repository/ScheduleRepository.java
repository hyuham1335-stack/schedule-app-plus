package com.scheduleappplus.schedule.repository;

import com.scheduleappplus.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE :writer IS NULL OR s.user.name = :writer")
    Page<Schedule> findAllByWriter(@Param("writer") String writer, Pageable pageable);
}
