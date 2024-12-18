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

public class EventDTO {
    private Integer id;

    private String name;

    private LocalDate date;

    private String place;

    private String content;

    private String image;

    private Integer areaId;

    private Integer adminId;

}
