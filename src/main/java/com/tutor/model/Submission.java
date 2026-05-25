package com.tutor.model;

import com.tutor.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "submissions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    @Column(name = "ai_feedback")
    private String aiFeedback;

    private int score;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "submitted_at")
    private Date submittedAt;

}
