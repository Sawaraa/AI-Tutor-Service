package com.tutor.service.Impl;

import com.tutor.dto.SubmissionDTO;
import com.tutor.model.*;
import com.tutor.model.enums.Role;
import com.tutor.model.enums.Status;
import com.tutor.model.enums.Type;
import com.tutor.repository.ClassroomMemberRepository;
import com.tutor.repository.LessonRepository;
import com.tutor.repository.SubmissionRepository;
import com.tutor.service.SubmissionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final ClassroomMemberRepository classroomMemberRepository;
    private final LessonRepository lessonRepository;
    private final SubmissionRepository  submissionRepository;
    private final AiGradingService aiGradingService;

    public SubmissionServiceImpl(ClassroomMemberRepository classroomMemberRepository,
                                 LessonRepository lessonRepository,
                                 SubmissionRepository submissionRepository,
                                 AiGradingService aiGradingService) {
        this.classroomMemberRepository = classroomMemberRepository;
        this.lessonRepository = lessonRepository;
        this.submissionRepository = submissionRepository;
        this.aiGradingService = aiGradingService;
    }

    @Override
    public SubmissionDTO submitAnswer(Long classroomId, Long lessonId, SubmissionDTO submissionDTO, Long userId) {

        ClassroomMember member = classroomMemberRepository
                .findByClassroomIdAndUserId(classroomId, userId)
                .orElseThrow(() -> new RuntimeException("You are not a member of this classroom"));

        if(member.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can submit answers");
        }

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson not found"));

        if (lesson.getType() != Type.TASK) {
            throw new RuntimeException("You can only submit answers for tasks");
        }

        submissionRepository.findByLessonIdAndStudentId(lessonId, userId)
                .ifPresent(s -> {
                    throw new RuntimeException("You already submitted this task");
                });

        User student = new User();
        student.setId(userId);

        Submission submission = Submission.builder()
                .lesson(lesson)
                .student(student)
                .answerFileOrUrl(submissionDTO.getAnswerFileOrUrl())
                .status(Status.SENT)
                .submittedAt(new Date())
                .build();

        Submission saved = submissionRepository.save(submission);

        saved.setStatus(Status.AI_CHECKING);
        submissionRepository.save(submission);

        AiResult aiResult = aiGradingService.gradeSubmission(
                lesson.getContent(),
                saved.getAnswerFileOrUrl(),
                lesson.getMaxScore()
        );

        saved.setAiScore(aiResult.getScore());
        saved.setAiFeedback(aiResult.getFeedback());
        saved.setStatus(Status.AI_REVIEWED);
        submissionRepository.save(saved);

        return toDTO(saved);
    }

    @Override
    public SubmissionDTO getMySubmission(Long lessonId, Long userId) {
        return null;
    }

    @Override
    public List<SubmissionDTO> getAllSubmissions(Long lessonId, Long userId) {
        return List.of();
    }

    @Override
    public SubmissionDTO gradeSubmission(Long submissionId, Integer tutorScore, Long userId) {
        return null;
    }

    private SubmissionDTO toDTO(Submission submission) {
        return SubmissionDTO.builder()
                .id(submission.getId())
                .lessonId(submission.getLesson().getId())
                .studentId(submission.getStudent().getId())
                .answerFileOrUrl(submission.getAnswerFileOrUrl())
                .aiFeedback(submission.getAiFeedback())
                .aiScore(submission.getAiScore())
                .tutorScore(submission.getTutorScore())
                .status(submission.getStatus())
                .submittedAt(submission.getSubmittedAt())
                .build();
    }
}
