package com.fpoly.backend.repository;

import com.fpoly.backend.entities.NoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NoteTypeRepository extends JpaRepository<NoteType,Integer> {
    @Query("SELECT nt.id as note_type_id, nt.name as name_note_type FROM NoteType nt ")
    List<Map<String, Object>> getAllNoteType();
}
