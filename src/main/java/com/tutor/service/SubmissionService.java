package com.tutor.service;

import com.tutor.dto.SubmissionDTO;

import java.util.List;

public interface SubmissionService {
    // Студент здає завдання
    SubmissionDTO submitAnswer(Long classroomId, Long lessonId, SubmissionDTO submissionDTO, Long userId);

    // Студент дивиться свою здачу + AI feedback
    SubmissionDTO getMySubmission(Long classroomId, Long lessonId, Long userId);

    // Репетитор дивиться всі здачі по завданню
    List<SubmissionDTO> getAllSubmissions(Long classroomId, Long lessonId, Long userId);

    // Репетитор ставить фінальну оцінку
    SubmissionDTO gradeSubmission(Long submissionId, Integer tutorScore, Long userId);

}
