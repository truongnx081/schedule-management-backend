package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.RoomDTO;
import com.fpoly.backend.entities.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class RoomMapper {

    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(target = "buildingName", source = "building.name")
    public abstract RoomDTO toDTO (Room room);

    @Mapping(target = "building", ignore = true)
    public abstract Room toEntity(RoomDTO roomDTO);

    @Mapping(target = "building", ignore = true)
    public abstract void updateRoom(@MappingTarget Room room, RoomDTO request);

}
