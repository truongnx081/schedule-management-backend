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
@Table(name = "semesters")
public class Semester {
    @Id
    @Column(name = "semester", columnDefinition = "VARCHAR(20)")
    private String semester;

    @JsonIgnore
    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<Clazz> clazzes;

    @JsonIgnore
    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<EducationProgram> educationPrograms;

    @JsonIgnore
    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<Student> students;

    @JsonIgnore
    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<SemesterProgress> semesterProgresses;
}
