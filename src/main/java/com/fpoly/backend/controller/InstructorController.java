package com.fpoly.backend.controller;

import com.fpoly.backend.dto.InstructorDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.InstructorService;
import com.fpoly.backend.until.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/instructors")
@Validated
public class InstructorController {
    @Autowired
    InstructorService instructorService;

    //Get all teaching schedule
    @GetMapping("/getAllTeachingSchedule")
    public ResponseEntity<Response> getTeachingSchedule(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    instructorService.getAllTeachingScheduleByInstructor(startDate, endDate),
                    "Get All Teaching Schedule Successful",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Get instructor information
    @GetMapping("/instructorInfor")
    public ResponseEntity<Response> getInstructorInfor() {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    instructorService.getInstructorInfor(),
                    "Get instructor Infor Succesful",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Lấy tất cả giảng viên theo bộ môn
    @GetMapping
    public ResponseEntity<Response> getAllInstructorBySpecialization(@RequestParam Integer specializationId) {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    instructorService.getAllInstructorBySpecialization(specializationId),
                    "Lấy tất cả giảng viên theo bộ môn thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Thêm mới giảng viên
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody InstructorDTO request) {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    instructorService.create(request),
                    "Thêm mới giảng viên thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Cập nhật giảng viên
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@RequestBody InstructorDTO request, @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    instructorService.update(request, id),
                    "Cập nhật giảng viên thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Xóa giảng viên
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Integer id) {
        try {
            instructorService.delete(id);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    null,
                    "Xóa giảng viên thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Import instructor by excel
    @PostMapping("/excel/upload")
    ResponseEntity<Response> uploadFileExcel(@RequestParam("file") MultipartFile file){

        String message = "";
        if (ExcelUtility.hasExcelFormat(file)) {
            try {
                instructorService.importInstructor(file);
                message = "The Excel file is uploaded: " + file.getOriginalFilename();
                return ResponseEntity.ok(new Response(LocalDateTime.now(), null, message, HttpStatus.OK.value()));
            } catch (Exception exp) {
                message = "The Excel file is not upload: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response(LocalDateTime.now(), null, exp.getMessage(), HttpStatus.EXPECTATION_FAILED.value()));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response(LocalDateTime.now(), null, message, HttpStatus.BAD_REQUEST.value()));
    }
}
