package com.fpoly.backend.controller;

import com.fpoly.backend.dto.BlockDTO;
import com.fpoly.backend.dto.MajorDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.MajorService;
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
@RequestMapping("/api/majors")
@Validated
public class MajorController {
    @Autowired
    private MajorService majorService;
    @GetMapping
    public ResponseEntity<Response> getAllMajor(){
        try{
            List<MajorDTO> majorDTOS = majorService.getAllMajor();
            return ResponseEntity.ok(new Response(LocalDateTime.now(),majorDTOS, "Lấy toàn bộ majors thành công", HttpStatus.OK.value()));
        }catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }

    }
}
