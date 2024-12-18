package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.WeekDayDTO;
import com.fpoly.backend.entities.WeekDay;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class WeekDayMapper {

    public abstract WeekDayDTO toDTO (WeekDay weekDay);

    public abstract WeekDay toEntity(WeekDayDTO weekDayDTO);

    public abstract void updateWeekDay(@MappingTarget WeekDay weekDay, WeekDayDTO request);

}
