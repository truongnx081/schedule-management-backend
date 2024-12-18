package com.fpoly.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class NoteDTO {
    private Integer id;

    private String content;

    private LocalDate date;

    private LocalTime noteTime;

    private String location;

    private Integer studentId;

    private Integer noteTypeId;
}
