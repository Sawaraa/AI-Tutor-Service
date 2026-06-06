package com.tutor.controller;

import com.tutor.dto.SubmissionDTO;
import com.tutor.service.SubmissionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classrooms/{classroomId}/lessons/{lessonId}/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    // Студент здає завдання
    @PostMapping
    public ResponseEntity<SubmissionDTO> submitAnswer(
            @PathVariable Long classroomId,
            @PathVariable Long lessonId,
            @RequestBody SubmissionDTO submissionDTO,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(submissionService.submitAnswer(classroomId, lessonId, submissionDTO, userId));
    }

//    // Студент дивиться свою здачу
//    @GetMapping("/my")
//    public ResponseEntity<SubmissionDTO> getMySubmission(
//            @PathVariable Long lessonId,
//            HttpServletRequest request) {
//        Long userId = (Long) request.getAttribute("userId");
//        return ResponseEntity.ok(submissionService.getMySubmission(lessonId, userId));
//    }
//
//    // Репетитор дивиться всі здачі
//    @GetMapping
//    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions(
//            @PathVariable Long lessonId,
//            HttpServletRequest request) {
//        Long userId = (Long) request.getAttribute("userId");
//        issionService.getAllSubmissions(lessonId, userId));
//    }
//
//    // Репетитор ставить фінальну оцінку
//    @PutMapping("/{submissionId}/grade")
//    public ResponseEntity<SubmissionDTO> gradeSubmission(
//            @PathVariable Long submissionId,
//            @RequestParam Integer tutorScore,
//            HttpServletRequest request) {
//        Long userId = (Long) request.getAttribute("userId");
//        return ResponseEntity.ok(submissionService.gradeSubmission(submissionId, tutorScore, userId));
//    }

}
