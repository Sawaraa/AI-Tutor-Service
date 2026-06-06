package com.tutor.service.Impl;

import com.tutor.dto.LessonDTO;
import com.tutor.model.Classroom;
import com.tutor.model.ClassroomMember;
import com.tutor.model.Lesson;
import com.tutor.model.enums.Role;
import com.tutor.repository.ClassroomMemberRepository;
import com.tutor.repository.LessonRepository;
import com.tutor.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ClassroomMemberRepository classroomMemberRepository;

    public LessonServiceImpl(LessonRepository lessonRepository, ClassroomMemberRepository classroomMemberRepository) {
        this.lessonRepository = lessonRepository;
        this.classroomMemberRepository = classroomMemberRepository;
    }

    @Override
    public LessonDTO createLesson(Long classroomId, LessonDTO lessonDTO, Long userId) {

        Classroom classroom = new Classroom();
        classroom.setId(classroomId);

        ClassroomMember member =  classroomMemberRepository.findByClassroomIdAndUserId(classroomId, userId)
                .orElseThrow(() -> new RuntimeException("You are not a member of this class"));

        if (member.getRole() != Role.OWNER) {
            throw new RuntimeException("Only tutor can create lessons");
        }

        Lesson lesson = Lesson.builder()
                .classroom(classroom)
                .title(lessonDTO.getTitle())
                .content(lessonDTO.getContent())
                .type(lessonDTO.getType())
                .fileOrUrl(lessonDTO.getFileOrUrl())
                .maxScore(lessonDTO.getMaxScore())
                .dueDate(lessonDTO.getDueDate())
                .createdAt(new Date())
                .build();

        Lesson saved = lessonRepository.save(lesson);

        return toDTO(saved);
    }

    @Override
    public List<LessonDTO> getLessonsByClassroom(Long classroomId, Long userId) {
        Classroom classroom = new Classroom();
        classroom.setId(classroomId);

        classroomMemberRepository.findByClassroomIdAndUserId(classroomId, userId)
                .orElseThrow(() -> new RuntimeException("You are not a member of this class"));

        List<Lesson> lessonsList = lessonRepository.findByClassroomIdOrderByCreatedAtDesc(classroom.getId());

        return lessonsList.stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public LessonDTO getLessonById(Long classroomId, Long lessonId, Long userId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson with id " + lessonId + " does not exist"));

        if (!lesson.getClassroom().getId().equals(classroomId)) {
            throw new RuntimeException("Lesson does not belong to this classroom");
        }

        classroomMemberRepository.findByClassroomIdAndUserId(lesson.getClassroom().getId(), userId)
                .orElseThrow(() -> new RuntimeException("You are not a member of this class"));

        return toDTO(lesson);
    }

    @Override
    public LessonDTO updateLesson(Long lessonId, LessonDTO lessonDTO, Long userId, Long classroomId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson with id " + lessonId + " does not exist"));

        if (!lesson.getClassroom().getId().equals(classroomId)) {
            throw new RuntimeException("Lesson does not belong to this classroom");
        }

        ClassroomMember member = classroomMemberRepository.findByClassroomIdAndUserId(lesson.getClassroom().getId(), userId)
                .orElseThrow(() -> new RuntimeException("You are not a member of this class"));


        if (member.getRole() != Role.OWNER) {
            throw new RuntimeException("Only tutor can edit lessons");
        }

        Lesson updated = Lesson.builder()
                .id(lessonId)
                .classroom(lesson.getClassroom())
                .title(lessonDTO.getTitle())
                .content(lessonDTO.getContent())
                .type(lessonDTO.getType())
                .fileOrUrl(lessonDTO.getFileOrUrl())
                .dueDate(lessonDTO.getDueDate())
                .createdAt(lesson.getCreatedAt())
                .build();

        Lesson saved = lessonRepository.save(updated);

        return toDTO(saved);
    }

    @Override
    public void deleteLesson(Long lessonId, Long userId, Long classroomId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson with id " + lessonId + " does not exist"));

        if (!lesson.getClassroom().getId().equals(classroomId)) {
            throw new RuntimeException("Lesson does not belong to this classroom");
        }

        ClassroomMember member = classroomMemberRepository.findByClassroomIdAndUserId(lesson.getClassroom().getId(), userId)
                .orElseThrow(() -> new RuntimeException("You are not a member of this class"));

        if (member.getRole() != Role.OWNER) {
            throw new RuntimeException("Only tutor can delit lessons");
        }

        lessonRepository.delete(lesson);
    }

    private LessonDTO toDTO(Lesson lesson) {
        return LessonDTO.builder()
                .id(lesson.getId())
                .classroomId(lesson.getClassroom().getId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .type(lesson.getType())
                .fileOrUrl(lesson.getFileOrUrl())
                .maxScore(lesson.getMaxScore())
                .dueDate(lesson.getDueDate())
                .createdAt(lesson.getCreatedAt())
                .build();
    }
}
