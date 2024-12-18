package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.PaymentDTO;
import com.fpoly.backend.services.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {

    @NonFinal
    @Value("${payos.clientId}") String clientId;

    @NonFinal
    @Value("${payos.apiKey}") String apiKey;

    @NonFinal
    @Value("${payos.checksumKey}") String checksumKey;

    @Override
    public String createPaymentLink(List<PaymentDTO> request) throws Exception {

        PayOS payOS = new PayOS(clientId, apiKey, checksumKey);
        String domain = "http://localhost:3000";
        Long orderCode = System.currentTimeMillis() / 1000;

        // Chuyển danh sách sản phẩm từ request thành ItemData
        List<ItemData> items = request.stream()
                .map(product -> ItemData.builder()
                        .name(product.getName())
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .build()).toList();

        // Tính tổng số tiền
        int totalAmount = items.stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Tạo PaymentData
        PaymentData paymentData = PaymentData.builder()
                .orderCode(orderCode)
                .amount(totalAmount)
                .description("Thanh toán tiền học phí")
                .returnUrl(domain + "/dang-ky-mon-hoc?orderCode=" + orderCode)
                .cancelUrl(domain + "/dang-ky-mon-hoc")
                .items(items) // Danh sách sản phẩm
                .build();

        // Gửi yêu cầu tạo link thanh toán
        CheckoutResponseData result = payOS.createPaymentLink(paymentData);

        return result.getCheckoutUrl();
    }
}
