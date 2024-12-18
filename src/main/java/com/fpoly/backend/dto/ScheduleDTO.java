package com.fpoly.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleDTO {
    private Integer id;

    private Integer clazzId;
    private LocalDate date;
    private String clazzCode;
    private String instructorCode;
    private Integer shiftId;
    private String roomName;
    private String subjectName;
    private String subjectCode;
    boolean status;
}
