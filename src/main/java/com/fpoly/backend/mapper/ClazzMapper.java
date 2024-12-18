package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.ClazzDTO;
import com.fpoly.backend.entities.Clazz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ClazzMapper {

    @Mapping(source = "block.block", target = "block")
    @Mapping(source = "semester.semester", target = "semester")
    @Mapping(source = "year.year", target = "year")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "instructor.id", target = "instructorId")
    @Mapping(source = "admin.id", target = "adminId")
    @Mapping(source = "shift.id", target = "shiftId")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(target = "weekdays", ignore = true)
    public abstract ClazzDTO toDTO (Clazz clazz);

    @Mapping(target = "block", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "year", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "admin", ignore = true)
    @Mapping(target = "shift", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "code", ignore = true)
    public abstract  void updateClazz(@MappingTarget Clazz clazz, ClazzDTO request);


    @Mapping(target = "block", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "year", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "admin", ignore = true)
    @Mapping(target = "shift", ignore = true)
    @Mapping(target = "room", ignore = true)
    public abstract Clazz toEntity(ClazzDTO clazzDTO);

}
