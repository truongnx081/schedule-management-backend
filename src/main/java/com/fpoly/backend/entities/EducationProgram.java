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
@Table(name = "education_programs")
public class EducationProgram extends AbstractEntity<Integer>{
    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester")
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year")
    private Year year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "private_major_id")
    private PrivateMajor privateMajor;

    @JsonIgnore
    @OneToMany(mappedBy = "educationProgram", fetch = FetchType.LAZY)
    private List<Student> students;

    @JsonIgnore
    @OneToMany(mappedBy = "educationProgram", fetch = FetchType.LAZY)
    private List<ApplyFor> applyFors;

}
