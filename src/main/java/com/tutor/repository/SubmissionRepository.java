package com.tutor.repository;

import com.tutor.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByLessonIdAndStudentId(Long lessonId, Long userId);
    List<Submission> findByLessonId(Long lessonId);
}
