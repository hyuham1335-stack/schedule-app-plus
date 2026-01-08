package com.scheduleappplus.schedule.repository;

import com.scheduleappplus.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE :writer IS NULL OR s.writer = :writer")
    List<Schedule> findAllByWriter(String writer);
}
