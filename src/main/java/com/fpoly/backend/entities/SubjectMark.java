package com.fpoly.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "subject_marks")
public class SubjectMark extends  AbstractEntity<Integer>{

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "part")
    private Integer part;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mark_column_id")
    private MarkColumn markColumn;


}
