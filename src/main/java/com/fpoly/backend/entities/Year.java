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
@Table(name = "years")
public class Year {
    @Id
    @Column(name = "year")
    private Integer year;

    @JsonIgnore
    @OneToMany(mappedBy = "year", fetch = FetchType.LAZY)
    private List<Student> students;

    @JsonIgnore
    @OneToMany(mappedBy = "year", fetch = FetchType.LAZY)
    private List<EducationProgram> educationPrograms;

    @JsonIgnore
    @OneToMany(mappedBy = "year", fetch = FetchType.LAZY)
    private List<Clazz> clazzes;

    @JsonIgnore
    @OneToMany(mappedBy = "year", fetch = FetchType.LAZY)
    private List<SemesterProgress> semesterProgresses;
}
