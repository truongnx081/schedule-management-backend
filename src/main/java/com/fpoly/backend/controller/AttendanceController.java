package com.fpoly.backend.controller;

import com.fpoly.backend.dto.AttendanceDTO;
import com.fpoly.backend.dto.ClazzDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/attendances")
@Validated
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    // Lấy danh sách điểm danh theo sinh viên
    @GetMapping("/student/{studentId}")
    public ResponseEntity<Response> getAttendanceByStudentId(@PathVariable Integer studentId){
        try {
            List<AttendanceDTO> attendanceDTOS = attendanceService.getAttendanceByStudentId(studentId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), attendanceDTOS, "Lấy danh sách điểm danh theo sinh viên thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @PostMapping("/attended")
    public ResponseEntity<Response> markAttendance(@RequestBody List<AttendanceDTO> attendanceDTOs) {
        try {
            List<AttendanceDTO> savedAttendanceDTOs = attendanceService.markAttendance(attendanceDTOs);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), savedAttendanceDTOs, "Điểm danh thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @GetMapping("/attendedByClazzId")
    public ResponseEntity<Response> getAttendanceByClazzId(
            @RequestParam("clazzId") Integer clazzId,
            @RequestParam("scheduleId") Integer scheduleId) {
        try {
            List<Map<String, Object>> list = attendanceService.getAttendanceByClazzId(clazzId,scheduleId);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    list,
                    "Lấy danh sách diểm danh thành công",
                    HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @PutMapping("/putAttended")
    public ResponseEntity<Response> updateAttendance(
            @RequestBody List<AttendanceDTO> attendanceDTOs) {
        try {
            List<AttendanceDTO> updatedAttendanceDTOs = attendanceService.updateAttendance(attendanceDTOs);

            return ResponseEntity.ok(new Response(LocalDateTime.now(), updatedAttendanceDTOs, "Cập nhật điểm danh thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @GetMapping("/attendance-for-retake")
    public ResponseEntity<Response> getStudentForAttendanceOfReatkeScheduleByClazzIdAndRetakeScheduleId(
            @RequestParam("clazzId") Integer clazzId,
            @RequestParam("retakeScheduleId") Integer retakeScheduleId) {
        try {
            List<Map<String, Object>> list = attendanceService.findRetakeAttendanceByClazzIdAndRetakeScheduleId(clazzId,retakeScheduleId);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    list,
                    "Lấy danh sách diểm danh thành công",
                    HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @PutMapping("/do-attendance-for-retake")
    public ResponseEntity<Response> doAttendanceForRetake(
            @RequestBody List<AttendanceDTO> attendanceDTOs) {
        try {
            List<AttendanceDTO> doAttendanceForRetake = attendanceService.doAttendanceForRetake(attendanceDTOs);

            return ResponseEntity.ok(new Response(LocalDateTime.now(), doAttendanceForRetake, "Cập nhật điểm danh thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @GetMapping("exist-retake-attendance")
    public ResponseEntity<Response> existByRetakeAttendance(@RequestParam("retakeScheduleId") Integer retakeScheduleId) {
        try {
            Boolean exist = attendanceService.checkExistByRetakeScheduleId(retakeScheduleId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), exist, "Điểm danh thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

}
