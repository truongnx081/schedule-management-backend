package com.fpoly.backend.controller;

import com.fpoly.backend.dto.ClazzDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.StudyInDTO;
import com.fpoly.backend.entities.SemesterProgress;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.ClazzService;
import com.fpoly.backend.until.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clazzs")
@Validated
public class ClazzController {
    @Autowired
    ClazzService clazzService;

    // Lấy danh sách lớp học
    @GetMapping
    public ResponseEntity<Response> getAll(){
        try {
            List<ClazzDTO> clazzs = clazzService.getAll();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), clazzs, "Lấy danh sách lớp học thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Lấy lớp học theo id
    @GetMapping("/{clazzId}")
    public ResponseEntity<Response> getOne(@PathVariable Integer clazzId){
        try {
            ClazzDTO clazz = clazzService.getOne(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), clazz, "Lấy lớp học thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Xóa lớp học theo id
    @DeleteMapping("/{clazzId}")
    public ResponseEntity<Response> delete(@PathVariable Integer clazzId){
        try {
            clazzService.delete(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), null, "Xóa lớp học thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Tạo mới 1 lớp học
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody ClazzDTO request){
        try {
            ClazzDTO clazz = clazzService.create(request);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), clazz, "Thêm lớp học thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Cập nhật 1 lớp học theo id
    @PutMapping("/{clazzId}")
    public ResponseEntity<Response> update(@RequestBody ClazzDTO request, @PathVariable Integer clazzId){
        try {
            ClazzDTO clazz = clazzService.update(request, clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), clazz, "Cập nhật lớp học thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Lấy danh sách lớp học theo block, semester và year
    @GetMapping("/clazzs-for-register")
    public ResponseEntity<Response> getClazzsByBlockAndSemesterAndYear(){
        try {
            List<Map<String, Object>> clazzs = clazzService.findClazzesForRegisterByBlockAndSemesterAndYearAndStudentId();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), clazzs, "Lấy danh sách lớp học theo block, semester và year thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Import clazz by excel
    @PostMapping("/excel/upload")
    ResponseEntity<Response> uploadFileExcel(@RequestParam("file") MultipartFile file){

        String message = "";
        if (ExcelUtility.hasExcelFormat(file)) {
            try {
                clazzService.importClazz(file);
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

    @GetMapping("/getClazzesByInstructor")
    public ResponseEntity<Response> getClazzesByInstructor(
            @RequestParam Integer block,
            @RequestParam String semester,
            @RequestParam Integer year) {
        try {
            List<Map<String, Object>> clazzes = clazzService.findClazzesByInstructorIdAndBlockAndSemesterAndYear(block, semester, year);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    clazzes,
                    "Lấy danh sách lớp học thành công",
                    HttpStatus.OK.value()
            ));
        } catch (AppUnCheckedException e) {
            // Handle application-specific exceptions and return an error response
            return ResponseEntity.status(e.getStatus()).body(new Response(
                    LocalDateTime.now(),
                    null,
                    e.getMessage(),
                    e.getStatus().value()
            ));
        }
    }



    //Lớp học hiện tại
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/current-clazzes")
    public ResponseEntity<Response> getCurrentClazzesByBlockAndSemesterAndYearAndStudentId(){
        try {
            List<Map<String, Object>> clazzes = clazzService.findCurrentClazzesByBlockAndSemesterAndYearAndStudentId();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), clazzes, "Lấy danh sách lớp học hiện tại cho sinh viên thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    //Lớp học được chọn để đổi ca
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/clazz-to-change")
    public ResponseEntity<Response> getClazzToChangeShiftById(@RequestParam("clazzId") Integer clazzId){
        try {
            Map<String, Object> clazz = clazzService.findClazzToChangeShiftById(clazzId);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), clazz, "Lấy lớp đã chọn để đổi môn học thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    //Danh sách các lớp học có môn muốn đổi và ca được chọn
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/clazzes-to-change")
    public ResponseEntity<Response> getClazzesToChangeShiftBySubjectIdAndShift(@RequestParam("subjectId") Integer subjectId,
                                                                               @RequestParam("shift") Integer shift){
        try {
            List<Map<String, Object>> clazzes = clazzService.findClazzesBySubjectIdAndShiftAndBlockAndSemesterAndYear(subjectId, shift);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), clazzes, "Lấy danh sách lớp học muốn đổi thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Lấy danh sách lớp học theo block, semester và year cho admin
    @GetMapping("/for-admin")
    public ResponseEntity<Response> getAllClazzsByBlockAndSemesterAndYear(
            @RequestParam("block") Integer block,
            @RequestParam("semester") String semester,
            @RequestParam("year") Integer year
    ){
        try {
            List<Map<String, Object>> clazzs = clazzService.findAllClazzsByBlockAndSemesterAndYear(block,semester,year);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), clazzs, "Lấy danh sách lớp học theo block, semester và year cho admin thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/registed-clazzes")
    public ResponseEntity<Response> getRegistedClazzesByBlockAndSemesterAndYearAndStudentId(){
        try {
            List<Map<String, Object>> clazzes = clazzService.findRegistedClazzesByBlockAndSemesterAndYearAndStudentId();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), clazzes, "Lấy danh sách lớp học đã đăng ký cho sinh viên thành công", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }


}

