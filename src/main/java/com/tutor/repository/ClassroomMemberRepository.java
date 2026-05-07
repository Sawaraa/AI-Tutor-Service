package com.tutor.repository;

import com.tutor.model.ClassroomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassroomMemberRepository extends JpaRepository<ClassroomMember, Long> {
    List<ClassroomMember> findByClassroomId(Long classroomId);
    Optional<ClassroomMember> findByClassroomIdAndUserId(Long classroomId, Long userId);
    List<ClassroomMember> findByUserId(Long userId);
}
