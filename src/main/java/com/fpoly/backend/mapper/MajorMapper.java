package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.MajorDTO;
import com.fpoly.backend.entities.Major;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class MajorMapper {


    @Mapping(source = "specialization.id", target = "specializationId")
    public abstract MajorDTO toDTO (Major major);

    @Mapping(target = "specialization", ignore = true)
    public abstract void updateMajor(@MappingTarget Major major, MajorDTO request);

    @Mapping(target = "specialization", ignore = true)
    public abstract Major toEntity(MajorDTO majorDTO);

}

