package com.tutor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomDTO {

    private Long id;
    private String name;
    private String description;
    private String inviteCode;
    private Long tutorId;
    private String tutorName;
    private Date createdAt;
}
