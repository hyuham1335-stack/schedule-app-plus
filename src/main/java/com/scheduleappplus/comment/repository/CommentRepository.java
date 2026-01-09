package com.scheduleappplus.comment.repository;

import com.scheduleappplus.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.schedule.id = :scheduleId")
    List<Comment> findBySchedule(Long scheduleId);

    @Query("SELECT c FROM Comment c WHERE :writer IS NULL OR c.user.name = :writer")
    List<Comment> findByUser(String writer);
}
