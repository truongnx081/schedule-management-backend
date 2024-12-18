package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClazzDTO {

    private Integer id;

    private String code;

    private String onlineLink;

    private Integer quantity;

    private Integer block;

    private String semester;

    private Integer year;

    private Integer subjectId;

    private Integer instructorId;

    private Integer adminId;

    private Integer shiftId;

    private Integer roomId;

    private Integer head;

    private String weekdays;

}
