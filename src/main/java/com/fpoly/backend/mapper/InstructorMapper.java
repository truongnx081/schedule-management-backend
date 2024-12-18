package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.InstructorDTO;
import com.fpoly.backend.entities.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class InstructorMapper {


    @Mapping(source = "specialization.id", target = "specializationId")
    public abstract InstructorDTO toDTO (Instructor instructor);

    @Mapping(target = "specialization",ignore = true)
    public abstract  void updateInstructor(@MappingTarget Instructor instructor, InstructorDTO request);

    @Mapping(target = "specialization",ignore = true)
    public abstract Instructor toEntity(InstructorDTO instructorDTO);

}
