package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.ExamScheduleDTO;
import com.fpoly.backend.entities.ExamSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ExamScheduleMapper {

    @Mapping(source = "clazz.id", target = "clazzId")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "shift.id", target = "shiftId")
    public abstract ExamScheduleDTO toDTO (ExamSchedule examSchedule);

    @Mapping(target = "clazz",ignore = true)
    @Mapping(target = "room",ignore = true)
    @Mapping(target = "shift",ignore = true)
    public abstract  void updateExamSchedule(@MappingTarget ExamSchedule examSchedule, ExamScheduleDTO request);


    @Mapping(target = "clazz",ignore = true)
    @Mapping(target = "room",ignore = true)
    @Mapping(target = "shift",ignore = true)
    public abstract ExamSchedule toEntity(ExamScheduleDTO examScheduleDTO);

}
