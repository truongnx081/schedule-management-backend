package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.StudentService;
import com.fpoly.backend.services.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/years")
@Validated
public class YearController {

    @Autowired
    private YearService yearService;

    //Admin get all years
    @GetMapping
    public ResponseEntity<Response> getAllYears() {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    yearService.findAll(),
                    "Lấy tất cả năm thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @GetMapping("/get-all-years-with-default")
    public ResponseEntity<Response> getAllYearsWithDefault() {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    yearService.findAllYearsWithDefault(),
                    "Lấy tất cả năm thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
}
