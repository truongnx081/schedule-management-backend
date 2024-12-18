package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.EventDTO;
import com.fpoly.backend.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class EventMapper {

    @Mapping(source = "area.id", target = "areaId")
    @Mapping(source = "admin.id", target = "adminId")
    public abstract EventDTO toDTO (Event event);

    @Mapping(target = "area", ignore = true)
    @Mapping(target = "admin",ignore = true)
    @Mapping(target = "image",ignore = true)
    public abstract  void updateEvent(@MappingTarget Event event, EventDTO request);


    @Mapping(target = "area", ignore = true)
    @Mapping(target = "admin",ignore = true)
    public abstract Event toEntity(EventDTO eventDTO);

}
