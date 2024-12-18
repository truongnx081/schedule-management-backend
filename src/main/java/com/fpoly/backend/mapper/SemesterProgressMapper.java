package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.SemesterProgressDTO;
import com.fpoly.backend.entities.SemesterProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class SemesterProgressMapper {

    @Mapping(source = "block.block", target = "block")
    @Mapping(source = "semester.semester", target = "semester")
    @Mapping(source = "year.year", target = "year")
    public abstract SemesterProgressDTO toDTO (SemesterProgress semesterProgress);


    @Mapping(target = "block",ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "year",ignore = true)
    public abstract SemesterProgress toEntity(SemesterProgressDTO semesterProgressDTO);

    @Mapping(target = "block", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "year", ignore = true)
    public abstract void updateSemesterProgress(@MappingTarget SemesterProgress semesterProgress, SemesterProgressDTO request);
}
