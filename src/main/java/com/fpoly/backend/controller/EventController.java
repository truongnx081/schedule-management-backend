package com.fpoly.backend.controller;

import com.fpoly.backend.dto.EventDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.dto.StudentDTO;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.EventService;
import com.fpoly.backend.until.ExcelUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/events")
@Validated
public class EventController {

    @Autowired
    private EventService eventService;


    //Get All Event
    @GetMapping
    public ResponseEntity<Response> getAllEvent() {
            try {
                return ResponseEntity.ok(new Response(
                        LocalDateTime.now(),
                        eventService.getAllEvent(),
                        "Get All Event Successful",
                        HttpStatus.OK.value())
                );
            } catch (AppUnCheckedException e) {
                return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
            }
    }

    // Lấy danh sách sự kiện theo khu vực
    @GetMapping("/area")
    public ResponseEntity<Response> getAllEventByArea(@RequestParam Integer areaId) {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    eventService.getAllEventByArea(areaId),
                    "Lấy danh sách sự kiện theo khu vực thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Thêm mới sự kiện
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> create(@RequestPart(value = "image", required = false) MultipartFile image,
                                           @RequestPart(value = "event") @Valid EventDTO request) {
        try {
            EventDTO eventDTO = eventService.create(request, image);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    eventDTO,
                    "Thêm mới sự kiện thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Cập nhật sự kiện
    @PutMapping
    public ResponseEntity<Response> update(@RequestPart(value = "image", required = false) MultipartFile image,
                                           @RequestPart(value = "event") @Valid EventDTO request,
                                           @RequestParam Integer id) {
        try {
            EventDTO eventDTO = eventService.update(request, image, id);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    eventDTO,
                    "Cập nhật sự kiện thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

    // Xóa sự kiện
    @DeleteMapping
    public ResponseEntity<Response> delete(@RequestParam Integer id) {
        try {
            eventService.delete(id);
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    null,
                    "Xóa sự kiện thành công",
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
                eventService.importEvent(file);
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
