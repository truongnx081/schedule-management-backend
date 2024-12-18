package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.SemesterDTO;
import com.fpoly.backend.entities.Semester;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class SemesterMapper {

    public abstract SemesterDTO toDTO (Semester semester);

    public abstract Semester toEntity(SemesterDTO semesterDTO);

    public abstract void updateSemester(@MappingTarget Semester semester, SemesterDTO request);
}
