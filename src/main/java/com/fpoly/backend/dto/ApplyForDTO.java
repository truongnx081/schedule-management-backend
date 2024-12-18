package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ApplyForDTO {

    private Integer id;

    private Integer subjectId;

    private Integer educationProgramId;
}
