package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.SubjectMarkService;
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
@RequestMapping("/api/subjectmarks")
@Validated
public class SubjectMarkController {
    @Autowired
    SubjectMarkService subjectMarkService;

    @GetMapping("/subject-mark-by-clazz")
    public ResponseEntity<Response> getSubjectMarkByClazzId(@RequestParam("clazzId") Integer clazzId) {
        try {
            List<Map<String, Object>> getAllMarkColumn = subjectMarkService.findSubjectMarkByClazzId(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllMarkColumn, "Đã lấy bảng điểm thành công!  ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    @GetMapping("/subject")
    public ResponseEntity<Response> getSubjectMarkBySubjectId(@RequestParam("subjectId") Integer subjectId) {
        try {
            List<Map<String, Object>> getAllMarkColumn = subjectMarkService.findSubjectMarkBySubjectId(subjectId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllMarkColumn, "Lấy danh sách cột điểm theo môn học thành công!  ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }
}
