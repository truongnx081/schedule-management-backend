package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RetakeScheduleDTO {
    private Integer id;

    private Date date;

    private Integer scheduleId;

    private Integer roomId;

    private Integer shiftId;

    private String roomName;


}
