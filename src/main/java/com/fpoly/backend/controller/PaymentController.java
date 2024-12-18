package com.fpoly.backend.controller;

import com.fpoly.backend.dto.PaymentDTO;
import com.fpoly.backend.dto.Response;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.PaymentService;
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
@RequestMapping("/api/payment")
@Validated
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    // payment
    @PostMapping("/create-payment-link")
    public ResponseEntity<Response> createPaymentLink(@RequestBody List<PaymentDTO> request) throws Exception {
        try {
            return ResponseEntity.ok(new Response(
                    LocalDateTime.now(),
                    paymentService.createPaymentLink(request),
                    "Tạo link thanh toán thành công",
                    HttpStatus.OK.value())
            );
        } catch (AppUnCheckedException e) {
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(), null, e.getMessage(), e.getStatus().value()));
        }
    }
}
