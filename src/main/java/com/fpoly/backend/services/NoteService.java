package com.fpoly.backend.services;

import com.fpoly.backend.dto.NoteDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NoteService {
    List<Map<String, Object>> countNotesByStudentIdAndMonthAndYear(Integer month, Integer year);

    List<Map<String, Object>> getAllNoteByMonthYear( Integer month, Integer year);

    List<Map<String, Object>> getAllNoteByDateMonthYear(Integer day, Integer month, Integer year);

    NoteDTO createNote(NoteDTO noteDTO);

    NoteDTO updateNote(Integer noteId, NoteDTO noteDTO);

    void deleteNote(Integer id);

    List<Map<String, Object>> getAllNote();
}
