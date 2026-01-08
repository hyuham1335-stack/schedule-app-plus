package com.scheduleappplus.schedule.service;

import com.scheduleappplus.schedule.dto.*;
import com.scheduleappplus.schedule.entity.Schedule;
import com.scheduleappplus.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request) {
        Schedule newSchedule = new Schedule(request.getWriter(), request.getTitle(), request.getContent());
        Schedule savedSchedule = scheduleRepository.save(newSchedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getWriter(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedDate(),
                savedSchedule.getLastModifiedDate()
        );
    }

    @Transactional(readOnly = true)
    public List<GetAllScheduleResponse> findAll(String writer) {
        List<Schedule> allSchedules = scheduleRepository.findAllByWriter(writer);

        return allSchedules.stream()
                .map(schedule -> new GetAllScheduleResponse(
                        schedule.getId(),
                        schedule.getWriter(),
                        schedule.getTitle(),
                        schedule.getContent()
                )).toList();
    }

    @Transactional(readOnly = true)
    public GetOneScheduleResponse findOne(Long scheduleId) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        return new GetOneScheduleResponse(
                findSchedule.getId(),
                findSchedule.getWriter(),
                findSchedule.getTitle(),
                findSchedule.getContent()
        );
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        findSchedule.updateSchedule(request.getTitle(), request.getContent());

        return new UpdateScheduleResponse(
                findSchedule.getId(),
                findSchedule.getWriter(),
                findSchedule.getTitle(),
                findSchedule.getContent(),
                findSchedule.getCreatedDate(),
                findSchedule.getLastModifiedDate()
        );
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);

        if (!existence) {
            throw new IllegalStateException("존재하지 않는 일정입니다.");
        }

        scheduleRepository.deleteById(scheduleId);
    }
}
