package com.fpoly.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "notifications")
public class Notification extends AbstractEntity<Integer>{
    @Column(name = "content", columnDefinition = "NVARCHAR(255)")
    private String content;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_student_id")
    private Student receiveStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_admin_id")
    private Admin receiveAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_instructor_id")
    private Instructor receiveInstructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_student_id")
    private Student sendStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_admin_id")
    private Admin sendAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_instructor_id")
    private Instructor sendInstructor;

}
