package com.scheduleappplus.schedule.controller;

import com.scheduleappplus.authentification.dto.SessionUser;
import com.scheduleappplus.exception.UnauthorizedException;
import com.scheduleappplus.schedule.dto.*;
import com.scheduleappplus.schedule.service.ScheduleService;
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
            @RequestBody CreateScheduleRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser
    ) {
        if(loginUser == null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(request, loginUser.getId()));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<GetAllScheduleResponse>> getAllSchedule(@RequestParam(required = false) String writer) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(writer));
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request,
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
