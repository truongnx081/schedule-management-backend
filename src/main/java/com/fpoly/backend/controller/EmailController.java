package com.fpoly.backend.controller;

import com.fpoly.backend.dto.EmailRequest;
import com.fpoly.backend.dto.Response;

import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class EmailController {
    @Autowired
    private EmailService emailService;

    //Gui email
    @PostMapping("/sendemail")
    public ResponseEntity<Response> sentEmail() {
        try {
            emailService.sendEmail();
            return ResponseEntity.ok(new Response(LocalDateTime.now(), null, "Sent email successful", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }

}
