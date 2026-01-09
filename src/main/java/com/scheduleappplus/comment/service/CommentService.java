package com.scheduleappplus.comment.service;

import com.scheduleappplus.authentification.exception.UnauthorizedException;
import com.scheduleappplus.comment.dto.*;
import com.scheduleappplus.comment.entity.Comment;
import com.scheduleappplus.comment.exception.CommentNotFoundException;
import com.scheduleappplus.comment.repository.CommentRepository;
import com.scheduleappplus.schedule.entity.Schedule;
import com.scheduleappplus.schedule.exception.ScheduleNotFoundException;
import com.scheduleappplus.schedule.repository.ScheduleRepository;
import com.scheduleappplus.user.entity.User;
import com.scheduleappplus.user.exception.UserNotFoundException;
import com.scheduleappplus.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateCommentResponse createComment(Long scheduleId, CreateCommentRequest request, Long userId) {
        Schedule Schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("존재하지 않는 일정입니다.")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 유저입니다.")
        );

        Comment newComment = new Comment(request.getContent(), Schedule, user);
        Comment savedComment = commentRepository.save(newComment);

        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getUser().getName(),
                savedComment.getCreatedDate(),
                savedComment.getLastModifiedDate()
        );
    }

    @Transactional(readOnly = true)
    public List<GetAllCommentByScheduleResponse> getAllCommentBySchedule(Long scheduleId) {

        checkScheduleExists(scheduleId);

        List<Comment> findComments = commentRepository.findBySchedule(scheduleId);

        return findComments.stream()
                .map(comment -> new GetAllCommentByScheduleResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getName()
                )).toList();
    }

    @Transactional(readOnly = true)
    public List<GetAllCommentByUserResponse> getAllCommentByUser(String writer) {
        List<Comment> findComments = commentRepository.findByUser(writer);

        return findComments.stream()
                .map(comment -> new GetAllCommentByUserResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getName()
                )).toList();
    }

    @Transactional(readOnly = true)
    public GetCommentResponse getComment(Long scheduleId, Long commentId) {

        checkScheduleExists(scheduleId);

        Comment findComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("존재하지 않는 댓글입니다.")
        );

        if(!findComment.getSchedule().getId().equals(scheduleId)) {
            throw new CommentNotFoundException("존재하지 않는 댓글입니다.");
        }

        return new GetCommentResponse(
                findComment.getId(),
                findComment.getContent(),
                findComment.getUser().getName()
        );
    }

    @Transactional
    public UpdateCommentResponse updateComment(Long commentId, @Valid UpdateCommentRequest request, Long userId) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("존재하지 않는 댓글입니다.")
        );

        if(!findComment.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("댓글 수정 권한이 없습니다.");
        }

        findComment.updateComment(request.getContent());

        return new UpdateCommentResponse(
                findComment.getId(),
                findComment.getContent(),
                findComment.getUser().getName(),
                findComment.getCreatedDate(),
                findComment.getLastModifiedDate()
        );
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("존재하지 않는 댓글입니다.")
        );

        if(!findComment.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("댓글 삭제 권한이 없습니다.");
        }

        commentRepository.deleteById(findComment.getId());
    }


    private void checkScheduleExists(Long scheduleId) {
        if(scheduleRepository.findById(scheduleId).isEmpty()) {
            throw new ScheduleNotFoundException("존재하지 않는 일정입니다.");
        }
    }
}
