package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.RetakeScheduleDTO;
import com.fpoly.backend.dto.ShiftDTO;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.RetakeScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/retakeschedules")
@Validated
public class RetakeScheduleController {

    @Autowired
    private RetakeScheduleService retakeScheduleService;

    //Tạo lịch học lại
    @PostMapping("/createRetakeSchedule")
    public ResponseEntity<Response> createRetakeSchedule(@RequestBody RetakeScheduleDTO retakeScheduleDTO) {
        try {

            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    retakeScheduleService.createRetakeSchedule(retakeScheduleDTO),
                    "Retake schedule created successfully",
                    HttpStatus.CREATED.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(
                    LocalDateTime.now(),
                    null,
                    e.getMessage(),
                    e.getStatus().value())
            );
        }
    }

    @GetMapping("/getRetakeScheduleByInstructor")
    public ResponseEntity<Response> getRetakeScheduleByInstructor(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        try {

            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    retakeScheduleService.getRetakeScheduleByInstructor(startDate,endDate),
                    "Get Retake schedule successfully",
                    HttpStatus.CREATED.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(
                    LocalDateTime.now(),
                    null,
                    e.getMessage(),
                    e.getStatus().value())
            );
        }
    }

}
