package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.StudyInDTO;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.StudyInService;
import org.apache.hc.core5.http.impl.io.AbstractMessageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/studyins")
@Validated
public class StudyInController {
    @Autowired
    StudyInService studyInService;

    // Lấy danh sách lớp học có sinh viên theo block, học kỳ và năm
    @GetMapping
    public ResponseEntity<Response> getAllByBlockAndSemesterAnhYear(){
        try {
            List<StudyInDTO> studyIns = studyInService.findAllByBlockAndSemesterAnhYear();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), studyIns, "Lấy danh sách lớp học có sinh viên theo block, học kỳ và năm học thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Lấy điểm trung bình của tất cả sinh viên theo clazz id
    @GetMapping("/getAllAverage")
    public ResponseEntity<Response> getAllMarkAverageStudentsByClazzId(@RequestParam Integer clazzId){
        try {
            List<Map<String, Object>> getAllMarkAverageStudentsByClazzId = studyInService.getAllMarkAverageStudentsByClazzId(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(),getAllMarkAverageStudentsByClazzId , "Lấy danh sách điểm trung bình của tất cả sinh viên theo id của lớp học thành công !", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Response> create(@RequestParam("clazzId") Integer clazzId){
        try {
            StudyInDTO createdStudyIn = studyInService.create(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(),createdStudyIn , "Đăng ký lớp học thành công!", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @PutMapping("/change-clazz")
    public ResponseEntity<Response> update(@RequestParam("oldClazzId") Integer oldClazzId, @RequestParam("newClazzId") Integer newClazzId){
        try {
            StudyInDTO createdStudyIn = studyInService.update(oldClazzId,newClazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(),createdStudyIn , "Đổi lớp học thành công!", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Response> delete(@RequestParam("clazzId") Integer clazzId){
        try {
            studyInService.delete(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), null, "Xóa đăng ký lớp học thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @PutMapping("/student")
    public ResponseEntity<Response> updateAllStudyInIsTrueByStudent(){
        try {
            studyInService.updateAllStudyInIsTrueByStudent();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), null, "Cập nhật trạng thái thanh toán thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @GetMapping("/student")
    public ResponseEntity<Response> getAllIdOfStudyInByBlockAndSemesterAndYearOfStudent2(){
        try {
            List<Map<String, Object>> studyIns = studyInService.getAllIdOfStudyInByBlockAndSemesterAndYearOfStudent2();
            return ResponseEntity.ok(
                    new Response(LocalDateTime.now(),
                            studyIns,
                            "Lấy danh sách lớp học hiện tại của sinh viên theo học kỳ thành công", HttpStatus.OK.value()
                    )
            );
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }


}
