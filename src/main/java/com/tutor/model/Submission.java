package com.tutor.model;

import com.tutor.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "submissions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    private User student;

    @Column(name = "answer_file_url")
    private String answerFileOrUrl;

    @Column(name = "ai_feedback", columnDefinition = "TEXT")
    private String aiFeedback;

    private int aiScore;

    @Column(name = "tutor_score")
    private Integer tutorScore;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "submitted_at")
    private Date submittedAt;

}
