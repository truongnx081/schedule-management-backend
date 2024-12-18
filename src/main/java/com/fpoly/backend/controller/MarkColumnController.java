package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.MarkColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/markcolumns")
@Validated
public class MarkColumnController {

    @Autowired
    MarkColumnService markColumnService;

    @GetMapping
    public ResponseEntity<Response> getAllMarkColumn() {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    markColumnService.getAll(),
                    "Lấy danh sách cột điểm thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
}
