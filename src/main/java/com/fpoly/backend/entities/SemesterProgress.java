package com.fpoly.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "semester_progresses")
public class SemesterProgress extends AbstractEntity<Integer> {

    @Column(name = "create_date_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateStart;

    @Column(name = "create_date_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateEnd;

    @Column(name = "repaire_date_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repaireDateStart;

    @Column(name = "repaire_date_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repaireDateEnd;

    @Column(name = "first_part_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstPartStart;

    @Column(name = "first_part_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstPartEnd;

    @Column(name = "second_part_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date secondPartStart;

    @Column(name = "second_part_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date secondPartEnd;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block")
    private Block block;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester")
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year")
    private Year year;
}
