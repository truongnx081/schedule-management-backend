package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.ApplyForDTO;
import com.fpoly.backend.entities.ApplyFor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ApplyForMapper {
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "educationProgram.id", target = "educationProgramId")
    public abstract ApplyForDTO toDTO (ApplyFor applyFor);

    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "educationProgram",ignore = true)
    public abstract  void updateAdmin(@MappingTarget ApplyFor applyFor, ApplyForDTO request);

    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "educationProgram",ignore = true)
    public abstract ApplyFor toEntity(ApplyForDTO applyForDTO);
}
