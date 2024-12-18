package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.AdminDTO;
import com.fpoly.backend.entities.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class AdminMapper {

    public abstract AdminDTO toDTO (Admin admin);

    public abstract  void updateAdmin(@MappingTarget Admin admin, AdminDTO request);


    public abstract Admin toEntity(AdminDTO adminDTO);
}
