package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.StudentDTO;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@Validated
public class StudentController {

    @Autowired
    private StudentService studentService;

    //Student get student information
    @GetMapping("/studentInfor")
    public ResponseEntity<Response> getStudentInfor() {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    studentService.getStudentInfor(),
                    "Get Student Infor Succesful",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    //Student update student information
    @PutMapping("/updateStudentByStudent")
    public ResponseEntity<Response> updateStudentByStudent(
            @RequestPart("request") @Valid StudentDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        try {
            System.out.println("Received Request: " + request);
            if (file != null) {
                System.out.println("Received File: " + file.getOriginalFilename());
            }
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    studentService.updateStudentByStudent(request, file),
                    "Update student successfully",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    //Admin get student information
    @GetMapping("/studentByID")
    public ResponseEntity<Response> getStudentById(@RequestParam Integer studentId) {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    studentService.getStudentById(studentId),
                    "Get student successfully",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    //Admin update student information
    @PutMapping("/updateStudentByAdmin")
    public ResponseEntity<Response> updateStudentByAdmin(
            @RequestParam Integer studentId,
            @RequestPart("student") StudentDTO request) {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    studentService.updateStudentByAdmin(studentId, request),
                    "Update student successfully",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(
                    new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value())
            );
        }
    }

    //Admin post student information
    @PostMapping("/createStudent")
    public ResponseEntity<Response> createStudent(@RequestPart("student") StudentDTO request,
                                                  @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            StudentDTO createdStudent = studentService.createStudent(request, file);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    createdStudent,
                    "Student created successfully",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    //Admin delete student
    @DeleteMapping("/deleteStudent")
    public ResponseEntity<Response> deleteStudent(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    studentService.deleteStudentById(id),
                    "Student deleted successfully",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Admin get student by course anh major
    @GetMapping("/getAllStudentByCourseAndMajor")
    public ResponseEntity<Response> getAllStudentByCourseAndMajor(
            @RequestParam(value = "course", required = false) String course,
            @RequestParam(value = "majorId", required = false) Integer majorId) {

        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    studentService.getAllStudentByCourseAndMajor(course, majorId),
                    "Get All Student successfully",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    //Admin import student from excel
    @PostMapping("/import")
    public ResponseEntity<Response> importStudents(@RequestParam("file") MultipartFile file) {
        try {
            List<StudentDTO> students = studentService.importExcelFile(file);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    students,
                    "Import Students successfully",
                    HttpStatus.OK.value())
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

    @GetMapping("/getStudentsByInstructorIdAndClazzId")
    public ResponseEntity<Response> getStudentsByInstructorIdAndClazzId(
            @RequestParam("clazzId") Integer clazzId) {

        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    studentService.getStudentsByClazzId(clazzId),
                    "Get All Students successfully",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Cập nhật avatar
    @PutMapping("/update-image/{id}")
    public ResponseEntity<Response> updateImage(@RequestParam(value = "avatar", required = false) MultipartFile avatar,
                                                @PathVariable Integer id){
        try {
            studentService.updateImage(id, avatar);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                null,
                    "Cập nhật hình đại diện thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }


    //Admin get student information
    @GetMapping("/course")
    public ResponseEntity<Response> getAllCourse() {
        try {
            List<Map<String, Object>> getAllCourse = studentService.getAllStudentByCourse();
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    getAllCourse,
                    "Get all course successfully",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @GetMapping("cannot-present")
    public ResponseEntity<Response> getCannotPresentStudentAmountByScheduleIdAndDateAndShift
                                                            (@RequestParam("scheduleId") Integer scheduleId,
                                                             @RequestParam("date") LocalDate date,
                                                             @RequestParam("shift") Integer shift){
        try {
            Integer amount = studentService.findCannotPresentStudentAmountByScheduleIdAndDateAndShift(scheduleId, date, shift);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    amount,
                    "Lấy số lượng sinh viên không thể có mặt thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
    @GetMapping("students-for-arrange-batch")
    public ResponseEntity<Response> getStudentWithQualifiByClazzId (@RequestParam("clazzId") Integer clazzId){
        try {
            List<Map<String, Object>> students = studentService.findStudentWithQualifyByClazzId(clazzId);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    students,
                    "Lấy danh sách sinh viên xếp lịch thi thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }



}
