package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectMarkColumn2DTO {
    private Integer id;
    private String name;
    private Double percentage;
    private Integer part;
}
