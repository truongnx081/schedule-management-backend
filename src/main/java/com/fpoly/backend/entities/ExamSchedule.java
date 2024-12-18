package com.fpoly.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "exam_schedules")
public class ExamSchedule extends AbstractEntity<Integer> {
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "batch")
    private Integer batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    private Shift shift;
}
