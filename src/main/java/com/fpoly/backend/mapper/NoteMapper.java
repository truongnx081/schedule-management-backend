package com.fpoly.backend.mapper;

import com.fpoly.backend.dto.NoteDTO;
import com.fpoly.backend.entities.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class NoteMapper {

    @Mapping(source = "noteType.id", target = "noteTypeId")
    @Mapping(source = "student.id", target = "studentId")
    public abstract NoteDTO toDTO (Note note);

    @Mapping(target = "noteType", ignore = true)
    @Mapping(target = "student", ignore = true)
    public abstract Note toEntity(NoteDTO noteDTO);

    @Mapping(target = "noteType", ignore = true)
    @Mapping(target = "student", ignore = true)
    public abstract void updateNote(@MappingTarget Note note, NoteDTO request);

}
