package com.fpoly.backend.controller;

import com.fpoly.backend.dto.ClazzDTO;
import com.fpoly.backend.dto.ExamScheduleDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.ExamScheduleService;
import com.fpoly.backend.until.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/examschedules")
@Validated
public class ExamScheduleController {
    @Autowired
    private ExamScheduleService examScheduleService ;

    //Get exam schedule
    @GetMapping("/getExamSchedules")
    public ResponseEntity<Response> getExamSchedulesByStudentIdAndStartDateAndEndDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        try {
            List<Map<String, Object>> examScheduleByDateRange = examScheduleService.getExameScheduleByDateRange( startDate, endDate);
            return ResponseEntity.ok(new Response(LocalDateTime.now(),examScheduleByDateRange , "Đã lấy thành công lịch thi theo ngày đã chọn ! ", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    // Lấy tất cả danh sách lịch thi cho admin
    @GetMapping("/admin")
    public ResponseEntity<Response> getAllExamOfAdmin(){
        try {
            List<Map<String, Object>> getAllExam = examScheduleService.getAllExamOfAdmin();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllExam, "Lấy danh sách lịch thi thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
    // Lấy tất cả danh sách lịch thi cho admin
    @GetMapping("/admin/sort")
    public ResponseEntity<Response> getAllExamByBlockAndSemesterAndYearAndSpecializationIdOfAdmin( @RequestParam Integer block,
                                                                                                 @RequestParam String semester,
                                                                                                 @RequestParam Integer year,
                                                                                                 @RequestParam Integer specializationId){
        try {
            List<Map<String, Object>> getAllExam = examScheduleService.getAllExamByBlockAndSemesterAndYearAndSpecializationIdOfAdmin(block, semester, year, specializationId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllExam, "Lấy danh sách lịch thi thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
    // Lấy lịch thi theo id
    @GetMapping("/{clazzId}")
    public ResponseEntity<Response> getOne(@PathVariable Integer clazzId){
        try {
            ExamScheduleDTO ExamScheduleDTO = examScheduleService.getOne(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), ExamScheduleDTO, "Lấy lịch thi theo id thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Xóa lịch thi theo id
    @DeleteMapping("/{clazzId}")
    public ResponseEntity<Response> delete(@PathVariable Integer clazzId){
        try {
            examScheduleService.delete(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), null, "Xóa lịch thi theo id thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Tạo mới 1 lịch thi
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody ExamScheduleDTO request){
        try {
            ExamScheduleDTO examScheduleDTO = examScheduleService.create(request);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), examScheduleDTO, "Thêm lịch thi thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Cập nhật lịch thi theo id
    @PutMapping("/{clazzId}")
    public ResponseEntity<Response> update(@RequestBody ExamScheduleDTO request, @PathVariable Integer clazzId){
        try {
            ExamScheduleDTO examScheduleDTO = examScheduleService.update(request, clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), examScheduleDTO, "Cập nhật lịch thi theo id thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Import ExamSchedule by excel
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/excel/upload")
    ResponseEntity<Response> uploadFileExcel(@RequestParam("file") MultipartFile file){
        String message = "";
        if (ExcelUtility.hasExcelFormat(file)) {
            try {
                examScheduleService.importExamSchedule(file);
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

    // load danh sach cac dot thi va sinh vien theo giang vien dang day lop do
    @GetMapping("/getExamBatch")
    public ResponseEntity<Response> getAllBatchByClassIdAndInstructorId(@RequestParam Integer clazzId) {
        try {
            List<Map<String, Object>> getAllByClassIdAndInstructorId = examScheduleService.getAllBathByClazzInstructor(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllByClassIdAndInstructorId, "Load danh sach thanh cong !", HttpStatus.OK.value()));
        }
        catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
}
