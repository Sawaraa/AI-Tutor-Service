package com.tutor.service.Impl;

import com.tutor.dto.ClassroomDTO;
import com.tutor.model.Classroom;
import com.tutor.model.ClassroomMember;
import com.tutor.model.User;
import com.tutor.model.enums.Role;
import com.tutor.repository.ClassroomMemberRepository;
import com.tutor.repository.ClassroomRepository;
import com.tutor.service.ClassroomService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    private final  ClassroomRepository classroomRepository;
    private final ClassroomMemberRepository classroomMemberRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository, ClassroomMemberRepository classroomMemberRepository) {
        this.classroomRepository = classroomRepository;
        this.classroomMemberRepository = classroomMemberRepository;
    }

    @Override
    public ClassroomDTO createClassroom(ClassroomDTO classroomDTO, Long tutorId) {
       User user = new User();
       user.setId(tutorId);

       Classroom classroom = Classroom.builder()
               .name(classroomDTO.getName())
               .description(classroomDTO.getDescription())
               .inviteCode(generateInviteCode())
               .tutor(user)
               .createdAt(new Date())
               .build();

       Classroom saved = classroomRepository.save(classroom);

       return ClassroomDTO.builder()
               .id(saved.getId())
               .name(saved.getName())
               .description(saved.getDescription())
               .inviteCode(saved.getInviteCode())
               .tutorId(saved.getTutor().getId())
               .tutorName(saved.getTutor().getFullName())
               .createdAt(saved.getCreatedAt())
               .build();

    }

    private String generateInviteCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    @Override
    public void joinClassroom(String inviteCode, Long userId) {
        User user = new User();
        user.setId(userId);

        Classroom classroom = classroomRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("No class with that code " + inviteCode + " was found"));

        classroomMemberRepository.findByClassroomIdAndUserId(classroom.getId(), userId)
                .ifPresent(member -> {
                    throw new RuntimeException("You are already in this class");
                });

        ClassroomMember classroomMember = ClassroomMember.builder()
                .classroom(classroom)
                .user(user)
                .role(Role.STUDENT)
                .build();

        classroomMemberRepository.save(classroomMember);
    }

    @Override
    public List<ClassroomDTO> getMyClassrooms(Long userId) {
        return classroomMemberRepository.findByUserId(userId).stream()
                .map(member -> ClassroomDTO.builder()
                        .id(member.getClassroom().getId())
                        .name(member.getClassroom().getName())
                        .description(member.getClassroom().getDescription())
                        .inviteCode(member.getClassroom().getInviteCode())
                        .tutorId(member.getClassroom().getTutor().getId())
                        .tutorName(member.getClassroom().getTutor().getFullName())
                        .createdAt(member.getClassroom().getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public ClassroomDTO getClassroomById(Long classroomId, Long userId) {
       Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("No class was found"));

       classroomMemberRepository.findByClassroomIdAndUserId(classroomId, userId)
                .orElseThrow(() -> new RuntimeException("You're not in this class"));

       return ClassroomDTO.builder()
                        .id(classroom.getId())
                        .name(classroom.getName())
                        .description(classroom.getDescription())
                        .inviteCode(classroom.getInviteCode())
                        .tutorId(classroom.getTutor().getId())
                        .tutorName(classroom.getTutor().getFullName())
                        .createdAt(classroom.getCreatedAt())
                        .build();
    }

    @Override
    public void deleteClassroom(Long classroomId, Long userId) {

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("No class was found"));

        ClassroomMember member = classroomMemberRepository.findByClassroomIdAndUserId(classroomId, userId)
                .orElseThrow(() -> new RuntimeException("No access"));

        if(member.getRole() != Role.OWNER) {
            throw new RuntimeException("Only the owner can delete a class");
        }

        List<ClassroomMember> members = classroomMemberRepository.findByClassroomId(classroomId);
        classroomMemberRepository.deleteAll(members);

        classroomRepository.delete(classroom);

    }
}
