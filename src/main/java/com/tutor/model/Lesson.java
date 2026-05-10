package com.tutor.model;

import com.tutor.model.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Type type;

    private  String fileOrUrl;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "created_at")
    private Date createdAt;
}
