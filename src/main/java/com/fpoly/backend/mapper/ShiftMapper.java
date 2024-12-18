package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.ShiftDTO;
import com.fpoly.backend.entities.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ShiftMapper {

    public abstract ShiftDTO toDTO (Shift shift);

    public abstract Shift toEntity(ShiftDTO shiftDTO);

    public abstract void updateShift(@MappingTarget Shift shift, ShiftDTO request);
}
