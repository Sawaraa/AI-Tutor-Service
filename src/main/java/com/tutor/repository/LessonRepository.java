package com.tutor.repository;

import com.tutor.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByClassroomIdOrderByCreatedAtDesc(Long classroomId);
}
