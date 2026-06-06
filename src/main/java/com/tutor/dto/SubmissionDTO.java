package com.tutor.dto;
import com.tutor.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private Long id;
    private Long lessonId;
    private Long studentId;
    private String answerFileOrUrl;
    private String aiFeedback;
    private int aiScore;
    private Integer tutorScore;
    private Status status;
    private Date submittedAt;
}
