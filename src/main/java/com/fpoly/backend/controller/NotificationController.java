package com.fpoly.backend.controller;

import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@Validated
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/student")
    public ResponseEntity<Response> getAllNotificationsByStudentId() {
        try {
            List<Map<String, Object>> getAllNotificationsByStudentId = notificationService.getAllNotificationByStudentId();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllNotificationsByStudentId, "Lấy thành công danh sách thông báo của studentId !", HttpStatus.OK.value()));
        }
        catch(AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
    @GetMapping("/admin")
    public ResponseEntity<Response> getAllNotificationsByAdminId() {
        try {
            List<Map<String, Object>> getAllNotificationsByAdminId = notificationService.getAllNotificationsByAdminId();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllNotificationsByAdminId, "Lấy thành công danh sách thông báo của adminId !", HttpStatus.OK.value()));
        }
        catch(AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
    @GetMapping("/instructor")
    public ResponseEntity<Response> getAllNotificationsByInstructorId() {
        try {
            List<Map<String, Object>> getAllNotificationsByInstructorId = notificationService.getAllNotificationsByInstructorId();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), getAllNotificationsByInstructorId, "Lấy thành công danh sách thông báo của instructorId !", HttpStatus.OK.value()));
        }
        catch(AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
}
