package com.tutor.service;

import com.tutor.dto.ClassroomDTO;

import java.util.List;

public interface ClassroomService {

    ClassroomDTO createClassroom(ClassroomDTO classroomDTO, Long tutorId);

    void joinClassroom(String inviteCode, Long userId);

    List<ClassroomDTO> getMyClassrooms(Long userId);

    ClassroomDTO getClassroomById(Long classroomId, Long userId);

    void deleteClassroom(Long classroomId, Long userId);
}
