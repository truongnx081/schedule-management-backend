package com.fpoly.backend.controller;

import com.fpoly.backend.dto.NoteDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.StudentDTO;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.IdentifyUserAccessService;
import com.fpoly.backend.services.NoteService;
import com.fpoly.backend.services.impl.IdentifyUserAccessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
@Validated
public class NoteController {
    @Autowired
    private NoteService noteService;

    // số lượng note mỗi loại
    @GetMapping("/countNoteByMonth")
    public ResponseEntity<Response> countNoteByMonthAndYear(@RequestParam Integer month, @RequestParam Integer year) {
        try {
            List<Map<String, Object>> countNoteByStudentIdAndMonthAndYear = noteService.countNotesByStudentIdAndMonthAndYear(month, year);
            return ResponseEntity.ok(new Response(LocalDateTime.now(),countNoteByStudentIdAndMonthAndYear , "Đã điếm thành công số ngày có ghi chú theo StudentID ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }
    // Load tất cả note có trong dữ liệu
    @GetMapping("/getAllNote")
    public ResponseEntity<Response> getAllNote() {
        try {
            List<Map<String, Object>> getAllNote = noteService.getAllNote();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllNote, "Đã lấy thành công tất cả ghi chú với studentId hiện tại ! ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    // Load note theo tháng năm
    @GetMapping("/getNoteByMonth")
    public ResponseEntity<Response> getAllNoteByMonthYear(@RequestParam Integer month, @RequestParam Integer year) {
        try {
            List<Map<String, Object>> getAllNoteByMonthYear = noteService.getAllNoteByMonthYear(month, year);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllNoteByMonthYear, "Đã lấy thành công có ghi chú theo tháng với studentId hiện tại ! ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    // get note theo ngày tháng năm
    @GetMapping("/getNoteByDay")
    public ResponseEntity<Response> getAllNoteByDateMonthYear(@RequestParam Integer day,@RequestParam Integer month, @RequestParam Integer year) {
        try {
            List<Map<String, Object>> getAllNoteByMonthYear = noteService.getAllNoteByDateMonthYear(day, month, year);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllNoteByMonthYear, "Đã lấy thành công ghi chú theo ngày với studentID hiện tại! ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }



    // create note
    @PostMapping("/createNote")
    public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDTO) {
        try {
            NoteDTO createdNote = noteService.createNote(noteDTO);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), createdNote, "Note được tạo thành công !", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // update Note
    @PutMapping("/updateNote")
    public ResponseEntity<Response> updateNote(@RequestParam Integer noteId, @RequestBody NoteDTO noteDTO) {
        try {
            NoteDTO updatedNote = noteService.updateNote(noteId, noteDTO);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), updatedNote, "Ghi chú đã được cập nhật thành công!", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @DeleteMapping("/deleteNote")
    public ResponseEntity<Response> deleteNote(@RequestParam Integer noteId) {
        try {
            noteService.deleteNote(noteId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), null, "Ghi chú đã được xóa thành công!", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
}
