package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.ScheduleDTO;
import com.fpoly.backend.entities.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ScheduleMapper {

    @Mapping(source = "clazz.id", target = "clazzId")
    public abstract ScheduleDTO toDTO (Schedule schedule);

    @Mapping(target = "clazz",ignore = true)
    public abstract Schedule toEntity(ScheduleDTO scheduleDTO);

    @Mapping(target = "clazz", ignore = true)
    public abstract void updateSchedule(@MappingTarget Schedule schedule, ScheduleDTO request);

}
