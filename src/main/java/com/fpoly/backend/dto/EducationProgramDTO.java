package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EducationProgramDTO {
    private Integer id;

    private String name;

    private String semester;

    private Integer year;

    private Integer privateMajorId;

}
