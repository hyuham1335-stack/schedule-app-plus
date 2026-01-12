package com.scheduleappplus.comment.controller;

import com.scheduleappplus.authentification.dto.SessionUser;
import com.scheduleappplus.authentification.domain.exception.UnauthorizedException;
import com.scheduleappplus.comment.dto.*;
import com.scheduleappplus.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser
    ) {
        if(loginUser == null){
            throw new UnauthorizedException("댓글 작성을 위해서는 로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(scheduleId, request, loginUser.getId()));
    }

    // 특정 일정에 달린 댓글 전체 조회
    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<GetAllCommentByScheduleResponse>> getAllCommentBySchedule(@PathVariable Long scheduleId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentBySchedule(scheduleId));
    }

    // 특정 사용자명을 통한 전체 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<List<GetAllCommentByUserResponse>> getAllCommentByUser(@RequestParam(required = false) String writer){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentByUser(writer));
    }

    // 단일 댓글 조회
    @GetMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<GetCommentResponse> getComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(scheduleId, commentId));
    }

    @PatchMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser
    ){
        if(loginUser == null){
            throw new UnauthorizedException("댓글 수정을 위해서는 로그인이 필요합니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentId, request, loginUser.getId()));
    }

    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser
    ){
        commentService.deleteComment(commentId, loginUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
