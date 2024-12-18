package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.SubjectDTO;
import com.fpoly.backend.entities.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class SubjectMapper {

    @Mapping(source = "required.id", target = "requiredId")
    @Mapping(source = "specialization.id", target = "specializationId")
    public abstract SubjectDTO toDTO (Subject subject);

    @Mapping(target = "required", ignore = true)
    @Mapping(target = "specialization", ignore = true)
    public abstract Subject toEntity(SubjectDTO subjectDTO);

    @Mapping(target = "required", ignore = true)
    @Mapping(target = "specialization", ignore = true)
    @Mapping(target = "code", ignore = true)
    public abstract void updateSubject(@MappingTarget Subject subject, SubjectDTO request);
}
