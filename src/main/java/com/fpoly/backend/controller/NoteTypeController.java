package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.NoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notetypes")
@Validated
public class NoteTypeController {
    @Autowired
    private  NoteTypeService noteTypeService;
    // get all note type
    @GetMapping("/geAllNoteType")
    public ResponseEntity<Response> getAllNoteType() {
        try {
            List<Map<String, Object>> getAllNoteType = noteTypeService.getAllNotType();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllNoteType, "Đã lấy thành công các loại ghi chú! ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }
}
