package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.SpecializationDTO;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@Validated
public class SpecializationController {
    @Autowired
    SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<Response>getAllSpecializations(){
        try{
            List<SpecializationDTO> specializations = specializationService.getAllSpecializations();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), specializations, "Đã tải lên toàn bộ bộ môn thành công", HttpStatus.OK.value()));
        }catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }
}
