package com.kcrana.ecommerce.payment;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.kcrana.ecommerce.payment.dto.CreatePaymentRequest;
import com.kcrana.ecommerce.payment.dto.PaymentResponse;
import com.kcrana.ecommerce.payment.entity.PaymentStatus;
import com.kcrana.ecommerce.payment.service.PaymentService;

@SpringBootTest
@ActiveProfiles("test")
class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    void createsPendingPaymentAndCanReadItBack() {
        PaymentResponse created = paymentService.createPayment(
                new CreatePaymentRequest("ORD-1001", "CUS-101", new BigDecimal("1499.00"), "inr"));

        PaymentResponse loaded = paymentService.getPayment(created.paymentId());

        assertThat(loaded.paymentId()).isEqualTo(created.paymentId());
        assertThat(loaded.orderId()).isEqualTo("ORD-1001");
        assertThat(loaded.amount()).isEqualByComparingTo("1499.00");
        assertThat(loaded.currency()).isEqualTo("INR");
        assertThat(loaded.status()).isEqualTo(PaymentStatus.PENDING);
    }
}
