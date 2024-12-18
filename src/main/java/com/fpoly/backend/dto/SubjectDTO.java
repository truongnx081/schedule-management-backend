package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectDTO {
    private Integer id;

    private String code;

    private String name;

    private Integer credits;

    private Integer total_hours;

    private String mission;

    private String description;

    private String note;

    private Boolean offline;

    private Boolean status;

    private Integer requiredId;

    private Integer specializationId;

}
