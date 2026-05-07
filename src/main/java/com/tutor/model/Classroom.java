package com.tutor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "classrooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @Column(name = "invite_code")
    private String inviteCode;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private User tutor;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "created_at")
    private Date createdAt;
}
