package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.SubjectMarkDTO;
import com.fpoly.backend.entities.SubjectMark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class SubjectMarkMapper {


    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "markColumn.id", target = "markColumnId")
    public abstract SubjectMarkDTO toDTO (SubjectMark subjectMark);

    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "markColumn", ignore = true)
    public abstract SubjectMark toEntity(SubjectMarkDTO subjectMarkDTO);

    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "markColumn", ignore = true)
    public abstract void updateSubjectMark(@MappingTarget SubjectMark subjectMark, SubjectMarkDTO request);
}
