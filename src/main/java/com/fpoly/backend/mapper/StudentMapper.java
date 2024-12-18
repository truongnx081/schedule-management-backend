package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.StudentDTO;
import com.fpoly.backend.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class StudentMapper {


    @Mapping(source = "semester.semester", target = "semester")
    @Mapping(source = "year.year", target = "year")
    @Mapping(source = "educationProgram.id", target = "educationProgramId")
    @Mapping(source = "major.id", target = "majorId")
    public abstract StudentDTO toDTO(Student student);

    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "year", ignore = true)
    @Mapping(target = "educationProgram",ignore = true)
    @Mapping(target = "major", ignore = true)
    public abstract Student toEntity(StudentDTO studentDTO);


    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "year", ignore = true)
    @Mapping(target = "educationProgram", ignore = true)
    @Mapping(target = "major", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "birthday", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "course", ignore = true)
    public abstract void updateStudent(@MappingTarget Student student, StudentDTO request);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "educationProgram", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "year", ignore = true)
    public abstract void updateStudentByAdmin(@MappingTarget Student student, StudentDTO request);

}
