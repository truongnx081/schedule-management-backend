package com.fpoly.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "notes")
public class Note extends AbstractEntity<Integer>{
    @Column(name = "content", columnDefinition = "NVARCHAR(255)")
    private String content;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "note_time")
    @Temporal(TemporalType.TIME)
    private LocalTime noteTime;

    @Column(name = "location", columnDefinition = "NVARCHAR(255)")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_type_id")
    private NoteType noteType;

}
