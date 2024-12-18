package com.fpoly.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "study_days")
public class StudyDay extends AbstractEntity<Integer>{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_day_id")
    private WeekDay weekDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;
}
