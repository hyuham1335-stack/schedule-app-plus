package com.scheduleappplus.schedule.controller;

import com.scheduleappplus.authentification.dto.SessionUser;
import com.scheduleappplus.authentification.exception.UnauthorizedException;
import com.scheduleappplus.schedule.dto.*;
import com.scheduleappplus.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @Valid @RequestBody CreateScheduleRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser
    ) {

        // 미로그인 시 401 상태코드로 응답 전송
        if(loginUser == null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(request, loginUser.getId()));
    }

    // 일정 목록 검색 시 사용자명으로도 검색 가능
    @GetMapping("/schedules")
    public ResponseEntity<List<GetAllScheduleResponse>> getAllSchedule(
            @RequestParam(required = false) String writer,
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(writer, pageNo, pageSize));
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser
    ){
        if(loginUser == null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(scheduleId, request, loginUser.getId()));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser
    )
    {
        if(loginUser == null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        scheduleService.deleteSchedule(scheduleId, loginUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
