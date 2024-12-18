package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.RetakeScheduleDTO;
import com.fpoly.backend.entities.RetakeSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class RetakeScheduleMapper {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "shift.id", target = "shiftId")
    @Mapping(source = "schedule.id", target = "scheduleId")
    public abstract RetakeScheduleDTO toDTO (RetakeSchedule retakeSchedule);

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "shift",ignore = true)
    @Mapping(target = "schedule", ignore = true)
    public abstract RetakeSchedule toEntity(RetakeScheduleDTO retakeScheduleDTO);

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "shift", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    public abstract void updateRetakeSchedule(@MappingTarget RetakeSchedule retakeSchedule, RetakeScheduleDTO request);

}
