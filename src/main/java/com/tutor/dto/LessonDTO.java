package com.tutor.dto;

import com.tutor.model.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {

    private Long id;
    private Long classroomId;
    private String title;
    private String content;
    private Type type;
    private String fileOrUrl;
    private Date dueDate;
    private Date createdAt;

}
