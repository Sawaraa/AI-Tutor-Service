package com.tutor.controller;

import com.tutor.dto.LessonDTO;
import com.tutor.service.LessonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms/{classroomId}/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(
            @PathVariable Long classroomId,
            @RequestBody LessonDTO lessonDTO,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(lessonService.createLesson(classroomId, lessonDTO, userId));
    }

    @GetMapping
    public ResponseEntity<List<LessonDTO>> getLessonsByClassroom(
            @PathVariable Long classroomId,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(lessonService.getLessonsByClassroom(classroomId, userId));
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonDTO> getLessonById(
            @PathVariable Long classroomId,
            @PathVariable Long lessonId,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(lessonService.getLessonById(classroomId, lessonId, userId));
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<LessonDTO> updateLesson(
            @PathVariable Long classroomId,
            @PathVariable Long lessonId,
            @RequestBody LessonDTO lessonDTO,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(lessonService.updateLesson(lessonId, lessonDTO, userId, classroomId));
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(
            @PathVariable Long classroomId,
            @PathVariable Long lessonId,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        lessonService.deleteLesson(lessonId, userId,classroomId );
        return ResponseEntity.ok().build();
    }

}
