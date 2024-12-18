package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.ArrangeBatchDTO;
import com.fpoly.backend.entities.ArrangeBatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ArrangeBatchMapper {
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "clazz.id", target = "clazzId")
    public abstract ArrangeBatchDTO toDTO (ArrangeBatch arrangeBatch);

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "clazz", ignore = true)
    public abstract  void updateArrangeBatch(@MappingTarget ArrangeBatch arrangeBatch, ArrangeBatchDTO request);


    @Mapping(target = "student", ignore = true)
    @Mapping(target = "clazz", ignore = true)
    public abstract ArrangeBatch toEntity(ArrangeBatchDTO arrangeBatchDTO);

}
