package com.fpoly.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplyForEducationDTO {
    private Set<String> ids;
    private  EducationProgramDTO educationProgramDTO;
}
