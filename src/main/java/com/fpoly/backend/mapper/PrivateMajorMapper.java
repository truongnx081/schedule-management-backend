package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.PrivateMajorDTO;
import com.fpoly.backend.entities.PrivateMajor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class PrivateMajorMapper {

    @Mapping(source = "major.id", target = "majorId")
    public abstract PrivateMajorDTO toDTO (PrivateMajor privateMajor);

    @Mapping(target = "major",ignore = true)
    public abstract PrivateMajor toEntity(PrivateMajorDTO privateMajorDTO);

    @Mapping(target = "major", ignore = true)
    public abstract void updatePrivateMajor(@MappingTarget PrivateMajor privateMajor, PrivateMajorDTO request);

}
