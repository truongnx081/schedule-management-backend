package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.EducationProgramDTO;
import com.fpoly.backend.entities.EducationProgram;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class EducationProgramMapper {

    @Mapping(source = "semester.semester", target = "semester")
    @Mapping(source = "year.year", target = "year")
    @Mapping(source = "privateMajor.id", target = "privateMajorId")
    public abstract EducationProgramDTO toDTO (EducationProgram educationProgram);

    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "year", ignore = true)
    @Mapping(target = "privateMajor", ignore = true)
    public abstract  void updateEducationProgram(@MappingTarget EducationProgram educationProgram, EducationProgramDTO request);


    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "year", ignore = true)
    @Mapping(target = "privateMajor", ignore = true)
    public abstract EducationProgram toEntity(EducationProgramDTO educationProgramDTO);

}
