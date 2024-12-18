package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.StudyDayDTO;
import com.fpoly.backend.entities.StudyDay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class StudyDayMapper {


    @Mapping(source = "weekDay.id", target = "weekDayId")
    @Mapping(source = "clazz.id", target = "clazzId")
    public abstract StudyDayDTO toDTO (StudyDay studyDay);

    @Mapping(target = "weekDay", ignore = true)
    @Mapping(target = "clazz", ignore = true)
    public abstract StudyDay toEntity(StudyDayDTO studyDayDTO);

    @Mapping(target = "weekDay", ignore = true)
    @Mapping(target = "clazz", ignore = true)
    public abstract void updateStudyDay(@MappingTarget StudyDay studyDay, StudyDayDTO request);
}
