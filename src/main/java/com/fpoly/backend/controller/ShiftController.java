package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.ShiftService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/shifts")
@Validated
public class ShiftController {

    @Autowired
    ShiftService shiftService;

    //Get all shift
    @GetMapping()
    public ResponseEntity<Response> getAllShift() {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                   shiftService.getAllShift(),
                    "Get Shift Successful",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    //Get all shift available
    @GetMapping("/getAllShiftAvailable")
    public ResponseEntity<Response> getAllShiftAvailable(
            @RequestParam("clazzId") Integer clazzId,
            @RequestParam("date") LocalDate date
    )
          {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    shiftService.getAvailableShifts(clazzId,date),
                    "Lấy ca học trống thành công",
                    HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @GetMapping("/available-shift")
    public ResponseEntity<Response> getAvailableShiftsByInstructorIdAndDate(
            @RequestParam("date") LocalDate date
    )
    {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    shiftService.findAvailableShiftsByInstructorIdAndDate(date),
                    "Lấy ca học trống thành công",
                    HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
}
