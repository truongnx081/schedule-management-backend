package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.SubjectDTO;
import com.fpoly.backend.dto.SubjectMarkColumnDTO;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.SubjectService;
import com.fpoly.backend.until.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subjects")
@Validated
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    // lấy tất cả môn học
    @GetMapping("/getAllSubject")
    public ResponseEntity<Response> getAllSubject() {
        try {
            List<Map<String, Object>> subjects = subjectService.findAllSubject();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), subjects, "Đã lấy thành công tất cả môn học", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    // Tìm kiếm môn học theo id bộ môn
    @GetMapping("/getAllSubjectBySpecializationId")
    public ResponseEntity<Response> getAllSubjectBySpecializationId(@RequestParam Integer specializationId) {
        try {
            List<Map<String, Object>> subjects = subjectService.getAllSubjectBySpecializationId(specializationId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), subjects, "Đã lấy thành công các môn học theo id =  " + specializationId, HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    // Tìm kiếm môn học theo id bộ môn
    @GetMapping("/getSubjectDetail")
    public ResponseEntity<Response> getSubjectById(@RequestParam Integer subjectId) {
        try {
            List<Map<String, Object>> subjects = subjectService.getSubjectDetail(subjectId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), subjects, "Lấy chi tiết môn hc thành công !", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    // Lấy danh sách môn học của chương trình học
    @GetMapping
    public ResponseEntity<Response> getAllSubjectByEducationProgramId(@RequestParam Integer educationProgramId){
        try {
            return ResponseEntity.ok(
                    new Response(LocalDateTime.now(),
                            subjectService.findAllSubjectByEducationProgramId(educationProgramId),
                            "Lấy danh sách môn học của chương trình học thành công",
                            HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Thêm mới môn học
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody SubjectMarkColumnDTO request){
        try {
            subjectService.create(request);
            return ResponseEntity.ok(
                    new Response(LocalDateTime.now(),
                            null,
                            "Thêm mới môn học thành công",
                            HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Cập nhật môn học
    @PutMapping
    public ResponseEntity<Response> update(@RequestBody SubjectMarkColumnDTO request,
                                           @RequestParam Integer subjectId){
        try {
            subjectService.update(request, subjectId);
            return ResponseEntity.ok(
                    new Response(LocalDateTime.now(),
                            null,
                            "Cập nhật môn học thành công",
                            HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Xóa môn học
    @DeleteMapping
    public ResponseEntity<Response> delete(@RequestParam Integer subjectId){
        try {
            subjectService.delete(subjectId);
            return ResponseEntity.ok(
                    new Response(LocalDateTime.now(),
                            null,
                            "Xóa môn học thành công",
                            HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Import subject by excel
    @PostMapping("/excel/upload")
    ResponseEntity<Response> uploadFileExcel(@RequestParam("file") MultipartFile file){
        String message = "";
        if (ExcelUtility.hasExcelFormat(file)) {
            try {
                subjectService.importSubject(file);
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
