package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.StudyResultDTO;
import com.fpoly.backend.entities.StudyResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class StudyResultMapper {

    @Mapping(source = "studyIn.id", target = "studyInId")
    @Mapping(source = "markColumn.id", target = "markColumnId")
    public abstract StudyResultDTO toDTO(StudyResult studyResult);

    @Mapping(target = "studyIn", ignore = true)
    @Mapping(target = "markColumn", ignore = true)
    public abstract StudyResult toEntity(StudyResultDTO studyResultDTO);

    @Mapping(target = "studyIn", ignore = true)
    @Mapping(target = "markColumn", ignore = true)
    public abstract void updateStudyResult(@MappingTarget StudyResult studyResult, StudyResultDTO request);

}
