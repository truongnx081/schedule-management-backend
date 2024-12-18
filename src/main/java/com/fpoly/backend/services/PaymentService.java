package com.fpoly.backend.services;

import com.fpoly.backend.dto.PaymentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
    String createPaymentLink(List<PaymentDTO> request) throws Exception;
}
