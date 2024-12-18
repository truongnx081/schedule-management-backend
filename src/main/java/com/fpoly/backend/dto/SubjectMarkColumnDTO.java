package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SubjectMarkColumnDTO {
    private Set<SubjectMarkColumn2DTO> items;
    private  SubjectDTO subjectDTO;
}
