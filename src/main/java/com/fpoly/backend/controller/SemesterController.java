package com.fpoly.backend.controller;

import com.fpoly.backend.dto.BlockDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.SemesterDTO;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.BlockService;
import com.fpoly.backend.services.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/semesters")
@Validated
public class SemesterController {
    @Autowired
    SemesterService semesterService;

    @GetMapping
    public ResponseEntity<Response> getAllSemester(){
        try{
            List<SemesterDTO> semesterDTOS = semesterService.getAllSemester();
            return ResponseEntity.ok(new Response(LocalDateTime.now(),semesterDTOS, "Lấy toàn bộ semester thành công", HttpStatus.OK.value()));
        }catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }

    }

    @GetMapping("get-all-semesters-with-default")
    public ResponseEntity<Response> getAllSemestersWithDefault(){
        try{
            List<Map<String, Object>> semesters = semesterService.findAllSemestersWithDefault();
            return ResponseEntity.ok(new Response(LocalDateTime.now(),semesters, "Lấy toàn bộ semester thành công", HttpStatus.OK.value()));
        }catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }

    }
}
