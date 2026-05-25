package com.tutor.service;

import com.tutor.dto.LessonDTO;

import java.util.List;

public interface LessonService {

    // Репетитор створює урок або завдання
    LessonDTO createLesson(Long classroomId, LessonDTO lessonDTO, Long userId);

    // Список всіх уроків в класі
    List<LessonDTO> getLessonsByClassroom(Long classroomId, Long userId);

    // Деталі одного уроку
    LessonDTO getLessonById(Long classroomId, Long lessonId, Long userId);

    // Редагувати урок
    LessonDTO updateLesson(Long lessonId, LessonDTO lessonDTO, Long userId, Long classroomId);

    // Видалити урок
    void deleteLesson(Long lessonId, Long userId, Long classroomId);

}
