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
@Table(name = "majors")
public class Major extends AbstractEntity<Integer>{
    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @JsonIgnore
    @OneToMany(mappedBy = "major", fetch = FetchType.LAZY)
    private List<PrivateMajor> privateMajors;

    @JsonIgnore
    @OneToMany(mappedBy = "major", fetch = FetchType.LAZY)
    private List<Student> students;
}
