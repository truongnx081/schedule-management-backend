package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.NoteDTO;
import com.fpoly.backend.entities.Note;
import com.fpoly.backend.entities.NoteType;
import com.fpoly.backend.entities.Student;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.mapper.NoteMapper;
import com.fpoly.backend.repository.NoteRepository;
import com.fpoly.backend.repository.NoteTypeRepository;
import com.fpoly.backend.repository.StudentRepository;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    @Autowired
    IdentifyUserAccessService identifyUserAccessService;
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NoteTypeRepository noteTypeRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Map<String, Object>> countNotesByStudentIdAndMonthAndYear(Integer month, Integer year) {
        Integer studentId = identifyUserAccessService.getStudent().getId();
        return noteRepository.countNotesByStudentIdAndMonthAndYear(studentId, month, year);
    }

    @Override
    public List<Map<String, Object>> getAllNoteByMonthYear( Integer month, Integer year) {
        Integer studentId = identifyUserAccessService.getStudent().getId();
        return noteRepository.getAllNoteByMonthYear(studentId, month, year);
    }

    @Override
    public List<Map<String, Object>> getAllNoteByDateMonthYear(Integer day, Integer month, Integer year) {
        Integer studentId = identifyUserAccessService.getStudent().getId();
        return noteRepository.getAllNoteByDayMonthYear(studentId,day, month, year);
    }

    @Override
    public List<Map<String, Object>> getAllNote() {
        Integer studentId = identifyUserAccessService.getStudent().getId();
        return noteRepository.getAllNote(studentId);
    }

    @Override
    public NoteDTO createNote(NoteDTO noteDTO) {
        String studentCode = identifyUserAccessService.getStudent().getCode();
        Integer studentId = identifyUserAccessService.getStudent().getId();
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppUnCheckedException("Student not found", HttpStatus.NOT_FOUND));
        Note note = noteMapper.toEntity(noteDTO);
        note.setCreatedBy(studentCode);
        note.setStudent(student);
        NoteType noteType = noteTypeRepository.findById(noteDTO.getNoteTypeId())
                .orElseThrow(() -> new AppUnCheckedException("Loại ghi chú không tồn tại", HttpStatus.NOT_FOUND));
        note.setNoteType(noteType);

        return noteMapper.toDTO(noteRepository.save(note));
    }

    @Override
    public NoteDTO updateNote(Integer noteId, NoteDTO noteDTO) {
        String studentCode = identifyUserAccessService.getStudent().getCode();
        // Kiểm tra xem ghi chú có tồn tại hay không
        Note existingNote = noteRepository.findById(noteId)
                .orElseThrow(() -> new AppUnCheckedException("Ghi chú không tồn tại", HttpStatus.NOT_FOUND));

        // Lấy thông tin sinh viên hiện tại
        Integer currentStudentId = identifyUserAccessService.getStudent().getId();

        // Kiểm tra xem sinh viên hiện tại có quyền cập nhật ghi chú này không
        if (!existingNote.getStudent().getId().equals(currentStudentId)) {
            throw new AppUnCheckedException("Bạn không có quyền cập nhật ghi chú này!", HttpStatus.FORBIDDEN);
        }

        // Cập nhật các thông tin từ noteDTO vào existingNote
        existingNote.setContent(noteDTO.getContent());
        existingNote.setDate(noteDTO.getDate());
        existingNote.setNoteTime(noteDTO.getNoteTime());
        existingNote.setLocation(noteDTO.getLocation());
        existingNote.setUpdatedBy(studentCode);
        // Nếu có thay đổi loại ghi chú
        if (noteDTO.getNoteTypeId() != null) {
            NoteType noteType = noteTypeRepository.findById(noteDTO.getNoteTypeId())
                    .orElseThrow(() -> new AppUnCheckedException("Loại ghi chú không tồn tại", HttpStatus.NOT_FOUND));
            existingNote.setNoteType(noteType);
        }

        // Lưu lại ghi chú đã cập nhật
        return noteMapper.toDTO(noteRepository.save(existingNote));
    }

    @Override
    public void deleteNote(Integer id) {
        // Kiểm tra xem ghi chú có tồn tại hay không
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new AppUnCheckedException("Ghi chú không tồn tại", HttpStatus.NOT_FOUND));

        // Lấy thông tin sinh viên hiện tại
        Integer currentStudentId = identifyUserAccessService.getStudent().getId();

        // Kiểm tra xem sinh viên hiện tại có quyền xóa ghi chú này không
        if (!existingNote.getStudent().getId().equals(currentStudentId)) {
            throw new AppUnCheckedException("Bạn không có quyền xóa ghi chú này!", HttpStatus.FORBIDDEN);
        }

        noteRepository.delete(existingNote);
    }




}
