package com.fpoly.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShiftDTO {
    private Integer id;

    private LocalTime startTime;

    private LocalTime endTime;
}
