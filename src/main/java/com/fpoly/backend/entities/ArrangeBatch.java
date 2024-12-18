package com.fpoly.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "arrange_batch")
public class ArrangeBatch extends AbstractEntity<Integer> {

    @Column(name = "batch", columnDefinition = "NVARCHAR(255)")
    private Integer batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}
