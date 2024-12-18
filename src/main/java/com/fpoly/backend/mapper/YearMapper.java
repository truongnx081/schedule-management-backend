package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.YearDTO;
import com.fpoly.backend.entities.Year;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class YearMapper {

    public abstract YearDTO toDTO (Year year);

    public abstract Year toEntity(YearDTO yearDTO);

    public abstract void updateYear(@MappingTarget Year year, YearDTO request);
}
