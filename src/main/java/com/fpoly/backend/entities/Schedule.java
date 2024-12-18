package com.fpoly.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "schedules")
public class Schedule extends AbstractEntity<Integer>{
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @JsonIgnore
    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<RetakeSchedule> retakeSchedules;

    @JsonIgnore
    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<Attendance> attendances;
}
