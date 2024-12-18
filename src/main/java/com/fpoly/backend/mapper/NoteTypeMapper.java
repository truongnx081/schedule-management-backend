package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.NoteTypeDTO;
import com.fpoly.backend.entities.NoteType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class NoteTypeMapper {

    public abstract NoteTypeDTO toDTO (NoteType noteType);

    public abstract NoteType toEntity(NoteTypeDTO noteTypeDTO);

    public abstract void updateNoteType(@MappingTarget NoteType noteType, NoteTypeDTO noteTypeDTO);

}
