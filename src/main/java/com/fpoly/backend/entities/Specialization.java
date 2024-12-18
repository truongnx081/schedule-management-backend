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
@Table(name = "specializations")
public class Specialization extends  AbstractEntity<Integer>{
    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @JsonIgnore
    @OneToMany(mappedBy = "specialization", fetch = FetchType.LAZY)
    private List<Instructor> instructors;

    @JsonIgnore
    @OneToMany(mappedBy = "specialization", fetch = FetchType.LAZY)
    private List<Subject> subjects;

    @JsonIgnore
    @OneToMany(mappedBy = "specialization", fetch = FetchType.LAZY)
    private List<Major> majors;
}
