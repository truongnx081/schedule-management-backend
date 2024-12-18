package com.fpoly.backend.controller;

import com.fpoly.backend.dto.*;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.EducationProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/educationprograms")
@Validated
public class EducationProgramController {
    @Autowired
    EducationProgramService educationProgramService;

    // Lấy danh sách chương trình đào tạo
    @GetMapping
    public ResponseEntity<Response> getAll(){
        try {
            List<EducationProgramDTO> educationProgramDTOS = educationProgramService.findAll();
            return ResponseEntity.ok(
                    new Response(LocalDateTime.now(),
                            educationProgramDTOS,
                            "Lấy danh sách chương trình đào tạo thành công",
                            HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Thêm mới chương trình đào tạo và môn học cho appyfor
    @PostMapping
    public ResponseEntity<Response> createApplyForEducation(@RequestBody ApplyForEducationDTO request){
        try {
            return ResponseEntity.ok(
                    new Response(LocalDateTime.now(),
                            educationProgramService.create(request),
                            "Thêm mới chương trình đào tạo và môn học cho appyfor thành công",
                            HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Cập nhật chương trình đào tạo và môn học cho appyfor
    @PutMapping
    public ResponseEntity<Response> updateApplyForEducation(@RequestBody ApplyForEducationDTO request,
                                                            @RequestParam Integer educationProgramId){
        try {
            return ResponseEntity.ok(
                    new Response(LocalDateTime.now(),
                            educationProgramService.update(request, educationProgramId),
                            "Cập nhật chương trình đào tạo và môn học cho appyfor thành công",
                            HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
}
