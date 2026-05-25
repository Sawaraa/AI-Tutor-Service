package com.tutor.controller;

import com.tutor.dto.ClassroomDTO;
import com.tutor.service.ClassroomService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    // Створити клас
    @PostMapping
    public ResponseEntity<ClassroomDTO> createClassroom(
            @RequestBody ClassroomDTO classroomDTO,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String userName = (String) request.getAttribute("userName");
        return ResponseEntity.ok(classroomService.createClassroom(classroomDTO, userId, userName));
    }

    // Приєднатись за кодом
    @PostMapping("/join/{inviteCode}")
    public ResponseEntity<Void> joinClassroom(
            @PathVariable String inviteCode,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        classroomService.joinClassroom(inviteCode, userId);
        return ResponseEntity.ok().build();
    }

    // Мої класи
    @GetMapping("/my")
    public ResponseEntity<List<ClassroomDTO>> getMyClassrooms(
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(classroomService.getMyClassrooms(userId));
    }

    // Деталі класу
    @GetMapping("/{classroomId}")
    public ResponseEntity<ClassroomDTO> getClassroomById(
            @PathVariable Long classroomId,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(classroomService.getClassroomById(classroomId, userId));
    }

    // Видалити клас
    @DeleteMapping("/{classroomId}")
    public ResponseEntity<Void> deleteClassroom(
            @PathVariable Long classroomId,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        classroomService.deleteClassroom(classroomId, userId);
        return ResponseEntity.ok().build();
    }
}
