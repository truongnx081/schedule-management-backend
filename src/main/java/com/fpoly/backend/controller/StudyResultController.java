package com.fpoly.backend.controller;


import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.StudyResultDTO;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.StudyResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/studyResult")
@Validated
public class StudyResultController {
    @Autowired
    StudyResultService studyResultService;

    //Get learning progress by student
//    @GetMapping("/learningProgressByStudent")
//    public ResponseEntity<Response> getLearningProgressByStudentId() {
//        try {
//            Map<String, Integer> learningProgress = studyResultService.getreportLearningProgressByStudentId();
//            return ResponseEntity.ok(new Response(
//                    LocalDateTime.now(),
//                    learningProgress,
//                    "Successful",
//                    HttpStatus.OK.value()));
//        } catch (AppUnCheckedException e) {
//            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
//        }
//    }


//    @GetMapping()
//    public ResponseEntity<Response> getStudyResultByStudentId() {
//        try {
//            List<Map<String, Object>> studyHistories = studyResultService.getAllStudyResultByStudentId();
//            return ResponseEntity.ok(new Response(LocalDateTime.now(), studyHistories, "Đã lấy thành công lịch sử các môn đã học theo studentId !  ", HttpStatus.OK.value()));
//        }
//        catch (AppUnCheckedException e) {
//            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
//        }
//    }

    // Đếm số lượng sinh viên pass và false môn học theo block, học kỳ và năm học
    @GetMapping("/{block}/{semester}/{year}")
    public ResponseEntity<Response> getAllByStudyInId(@PathVariable Integer block,
                                                      @PathVariable String semester,
                                                      @PathVariable Integer year) {
        try {
            Map<String, Object> studyResultDTOS = studyResultService.countPassAndFalseByBlockAndSemesterAndYearOfStudent(block, semester, year);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), studyResultDTOS, "Đếm số lượng môn học pass và false của sinh viên theo block, học kỳ và năm học thành công !  ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    // Load bảng điểm chi tiết của mỗi sinh vien tương ứng với clazzID
//    @GetMapping("/getMarkColumn")
//    public ResponseEntity<Response> getAllMarkColumn(@RequestParam Integer clazzId, @RequestParam Integer studentId) {
//        try {
//            List<Map<String, Object>> getAllMarkColumn = studyResultService.getAllMarkColumn(clazzId, studentId);
//            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllMarkColumn, "Đã lấy thành công các cột điểm theo clazzId và studentId thành công!  ", HttpStatus.OK.value()));
//        }
//        catch (AppUnCheckedException e) {
//            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
//        }
//    }
    // Laays bangr diem chi tiet tuwf lop hoc da ton tai trong study result
//    @GetMapping("/getMarkDetail")
//    public ResponseEntity<Response> getAllMarkDetail(@RequestParam Integer clazzId, @RequestParam Integer subjectId) {
//        try {
//            List<Map<String, Object>> getAllMarkColumn = studyResultService.getAllMarkDetail(clazzId, subjectId);
//            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllMarkColumn, "Đã lấy thành công các cột điểm theo clazzId và studentId thành công!  ", HttpStatus.OK.value()));
//        }
//        catch (AppUnCheckedException e) {
//            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
//        }
//    }
    // tạo bảng điểm cho sinh viên
//    @PostMapping("/create")
//    public ResponseEntity<Response> createStudyResult(@RequestParam Integer studentId, @RequestBody StudyResultDTO studyResultDTO) {
//        try {
//            StudyResultDTO createStudyResult = studyResultService.createStudyResult(studentId, studyResultDTO);
//            return ResponseEntity.ok(new Response(LocalDateTime.now(), createStudyResult, "Study Result được tạo thành công !", HttpStatus.OK.value()));
//        }catch (AppUnCheckedException e) {
//            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
//        }
//    }

    // cập nhật bảng điểm cho sinh viên
//    @PutMapping("/update")
//    public ResponseEntity<Response> updateStudyResult(@RequestParam Integer studyResultId, @RequestBody StudyResultDTO studyResultDTO) {
//        try {
//            StudyResultDTO updateStudyResult = studyResultService.updateStudyResult(studyResultId, studyResultDTO);
//            return ResponseEntity.ok(new Response(LocalDateTime.now(), updateStudyResult, "Study Result được cập nhật thành công !", HttpStatus.OK.value()));
//        }catch (AppUnCheckedException e) {
//            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
//        }
//    }

    //Lấy danh sách điểm của lớp
    @GetMapping("/getMarkByClazzId")
    public ResponseEntity<Response> getMarkByClazzId(@RequestParam Integer clazzId) {
        try {
            List<Map<String, Object>> getAllMarkColumn = studyResultService.getMarkByClazzId(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllMarkColumn, "Đã lấy điểm thành công!  ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }


    @GetMapping("/study-history")
    public ResponseEntity<Response> getStudyHistoryByStudentId() {
        try {
            List<Map<String, Object>> getAllMarkColumn = studyResultService.findStudyHistoryByStudentId();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllMarkColumn, "Đã lấy lịch sử học tập thành công!  ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    @GetMapping("/mark-table")
    public ResponseEntity<Response> getMarkTableByStudentId() {
        try {
            List<Map<String, Object>> getAllMarkColumn = studyResultService.findMarkTableByStudentId();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllMarkColumn, "Đã lấy bảng điểm thành công!  ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    @GetMapping("/mark-detail")
    public ResponseEntity<Response> getMarkDetailByStudyInId(@RequestParam("studyInId") Integer studyInId) {
        try {
            List<Map<String, Object>> getAllMarkColumn = studyResultService.findMarkDetailByStudyInId(studyInId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllMarkColumn, "Đã lấy bảng điểm thành công!  ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    @GetMapping("/mark-detail-in-clazz")
    public ResponseEntity<Response> getStudyResultByStudentIdAndClazzId(@RequestParam("studentId") Integer studentId,
                                                                        @RequestParam("clazzId") Integer clazzId) {
        try {
            List<Map<String, Object>> getAllMarkColumn = studyResultService.findStudyResultByStudentIdAndClazzId(studentId,clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllMarkColumn, "Đã lấy bảng điểm thành công!  ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    @PutMapping("/update-mark")
    public ResponseEntity<Response> updateMarkForStudent(@RequestParam("studentId") Integer studentId,
                                                         @RequestParam("clazzId") Integer clazzId,
                                                         @RequestBody List<StudyResultDTO> studyResultDTOS) {
        try {
            List<StudyResultDTO> updateMark = studyResultService.update(studentId,clazzId, studyResultDTOS);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), updateMark, "Đã lấy bảng điểm thành công!  ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }


}