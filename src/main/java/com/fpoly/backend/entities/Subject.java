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
@Table(name = "subjects")
public class Subject extends AbstractEntity<Integer>{
    @Column(name = "code", columnDefinition = "VARCHAR(20)", unique = true)
    private String code;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "total_hours")
    private Integer total_hours;

    @Column(name = "mission", columnDefinition = "NVARCHAR(255)")
    private String mission;

    @Column(name = "description", columnDefinition = "NVARCHAR(255)")
    private String description;

    @Column(name = "note", columnDefinition = "NVARCHAR(255)")
    private String note;

    @Column(name = "offline")
    private Boolean offline;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "required")
    private Subject required;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @JsonIgnore
    @OneToMany(mappedBy = "required", fetch = FetchType.LAZY)
    private List<Subject> requiredSubjects;

    @JsonIgnore
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<Clazz> clazzes;


    @JsonIgnore
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<ApplyFor> applyFors;

    @JsonIgnore
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<SubjectMark> subjectMarks;
}
