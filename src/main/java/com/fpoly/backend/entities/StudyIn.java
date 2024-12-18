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
@Table(name = "study_ins")
public class StudyIn extends AbstractEntity<Integer>{
    @Column(name = "paid")
    private boolean paid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "studyIn",fetch = FetchType.LAZY)
    @JsonIgnore
    List<StudyResult> studyResults;
}
