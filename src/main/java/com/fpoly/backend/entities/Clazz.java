package com.fpoly.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "clazzs")
public class Clazz extends AbstractEntity<Integer>{
    @Column(name = "code", columnDefinition = "VARCHAR(30)")
    private String code;

    @Column(name = "online_link", columnDefinition = "VARCHAR(255)")
    private String onlineLink;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block")
    private Block block;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester")
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year")
    private Year year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head")
    private Student student;

    @JsonIgnore
    @OneToMany(mappedBy = "clazz", fetch = FetchType.LAZY)
    private List<ExamSchedule> examSchedules;

    @JsonIgnore
    @OneToMany(mappedBy = "clazz", fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    @JsonIgnore
    @OneToMany(mappedBy = "clazz", fetch = FetchType.LAZY)
    private List<StudyIn> studyIns;

    @JsonIgnore
    @OneToMany(mappedBy = "clazz", fetch = FetchType.LAZY)
    private List<StudyDay> studyDays;

    @JsonIgnore
    @OneToMany(mappedBy = "clazz", fetch = FetchType.LAZY)
    private List<ArrangeBatch> arrangeBatches;
}
