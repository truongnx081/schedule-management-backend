package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.StudyInDTO;
import com.fpoly.backend.entities.StudyIn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class StudyInMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "clazz.id", target = "clazzId")
    public abstract StudyInDTO toDTO (StudyIn studyIn);

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "clazz", ignore = true)
    public abstract StudyIn toEntity(StudyInDTO studyInDTO);


    @Mapping(target = "student", ignore = true)
    @Mapping(target = "clazz", ignore = true)
    public abstract void updateStudyIn(@MappingTarget StudyIn studyIn, StudyInDTO request);

}
