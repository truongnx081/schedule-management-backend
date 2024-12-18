package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SubjectMarkDTO {
    private Integer id;

    private Integer subjectId;

    private Integer markColumnId;

    private Double percentage;
}
