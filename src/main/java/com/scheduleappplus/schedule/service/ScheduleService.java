package com.scheduleappplus.schedule.service;

import com.scheduleappplus.authentification.dto.SessionUser;
import com.scheduleappplus.exception.UnauthorizedException;
import com.scheduleappplus.schedule.dto.*;
import com.scheduleappplus.schedule.entity.Schedule;
import com.scheduleappplus.schedule.repository.ScheduleRepository;
import com.scheduleappplus.user.entity.User;
import com.scheduleappplus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        Schedule newSchedule = new Schedule(user, request.getTitle(), request.getContent());
        Schedule savedSchedule = scheduleRepository.save(newSchedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUser().getName(),
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
                        schedule.getUser().getName(),
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
                findSchedule.getUser().getName(),
                findSchedule.getTitle(),
                findSchedule.getContent()
        );
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request, Long userId) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        if(!findSchedule.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("수정 권한이 없습니다");
        }

        findSchedule.updateSchedule(request.getTitle(), request.getContent());

        return new UpdateScheduleResponse(
                findSchedule.getId(),
                findSchedule.getUser().getName(),
                findSchedule.getTitle(),
                findSchedule.getContent(),
                findSchedule.getCreatedDate(),
                findSchedule.getLastModifiedDate()
        );
    }

    @Transactional
    public void deleteSchedule(Long scheduleId, Long userId) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        if(!findSchedule.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("수정 권한이 없습니다");
        }

        scheduleRepository.deleteById(scheduleId);
    }
}
