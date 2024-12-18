package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.SpecializationDTO;
import com.fpoly.backend.entities.Specialization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class SpecializationMapper {

    @Mapping(source = "area.id", target = "areaId")
    public abstract SpecializationDTO toDTO (Specialization specialization);

    @Mapping(target = "area", ignore = true)
    public abstract Specialization toEntity(SpecializationDTO specializationDTO);

    @Mapping(target = "area", ignore = true)
    public abstract void updateSpecialization(@MappingTarget Specialization specialization, SpecializationDTO request);

}
