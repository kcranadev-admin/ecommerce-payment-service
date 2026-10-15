package com.kcrana.ecommerce.payment.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.kcrana.ecommerce.payment.entity.Payment;
import com.kcrana.ecommerce.payment.entity.PaymentStatus;

public record PaymentResponse(
        UUID paymentId,
        String orderId,
        String customerId,
        BigDecimal amount,
        String currency,
        PaymentStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getCustomerId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt());
    }
}
