package com.fpoly.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "shifts")
public class Shift extends AbstractEntity<Integer>{
    @Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;

    @JsonIgnore
    @OneToMany(mappedBy = "shift", fetch = FetchType.LAZY)
    private List<Clazz> clazzes;

    @JsonIgnore
    @OneToMany(mappedBy = "shift", fetch = FetchType.LAZY)
    private List<ExamSchedule> examSchedules;

    @JsonIgnore
    @OneToMany(mappedBy = "shift", fetch = FetchType.LAZY)
    private List<RetakeSchedule> retakeSchedules;
}
