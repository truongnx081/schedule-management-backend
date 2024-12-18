package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.AreaDTO;
import com.fpoly.backend.entities.Area;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class AreaMapper {

    public abstract AreaDTO toDTO (Area area);

    public abstract  void updateArea(@MappingTarget Area area, AreaDTO request);


    public abstract Area toEntity(AreaDTO areaDTO);
}
