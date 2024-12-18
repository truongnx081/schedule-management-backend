package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.BuildingDTO;
import com.fpoly.backend.entities.Building;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class BuildingMapper {

    @Mapping(source = "area.id", target = "areaId")
    public abstract BuildingDTO toDTO (Building building);

    @Mapping(target = "area", ignore = true)
    public abstract  void updateBuilding(@MappingTarget Building building, BuildingDTO request);


    @Mapping(target = "area", ignore = true)
    public abstract Building toEntity(BuildingDTO buildingDTO);

}
