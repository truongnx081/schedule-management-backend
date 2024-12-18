package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.MarkColumnDTO;
import com.fpoly.backend.entities.MarkColumn;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class MarkColumnMapper {

    public abstract MarkColumnDTO toDTO (MarkColumn markColumn);

    public abstract MarkColumn toEntity(MarkColumnDTO markColumnDTO);

    public abstract void updateMarkColumn(@MappingTarget MarkColumn markColumn, MarkColumnDTO request);

}
