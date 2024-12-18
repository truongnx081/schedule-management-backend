package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttendanceDTO {
    private Integer id;

    private Integer studentId;

    private Integer scheduleId;

    private Integer retakeScheduleId;

    private Boolean present;
}
