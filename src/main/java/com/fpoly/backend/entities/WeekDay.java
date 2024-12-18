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
@Table(name = "week_days")
public class WeekDay extends AbstractEntity<Integer> {
    @Column(name = "day", columnDefinition = "NVARCHAR(30)")
    private String day;

    @JsonIgnore
    @OneToMany(mappedBy = "weekDay", fetch = FetchType.LAZY)
    private List<StudyDay> studyDays;
}
