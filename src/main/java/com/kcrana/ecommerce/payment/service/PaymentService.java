package com.kcrana.ecommerce.payment.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kcrana.ecommerce.payment.dto.CreatePaymentRequest;
import com.kcrana.ecommerce.payment.dto.PaymentResponse;
import com.kcrana.ecommerce.payment.entity.Payment;
import com.kcrana.ecommerce.payment.exception.DuplicateOrderException;
import com.kcrana.ecommerce.payment.exception.PaymentNotFoundException;
import com.kcrana.ecommerce.payment.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        paymentRepository.findByOrderId(request.orderId())
                .ifPresent(existing -> { throw new DuplicateOrderException(request.orderId()); });

        Payment payment = new Payment(request.orderId(), request.customerId(), request.amount(), request.currency());
        return PaymentResponse.from(paymentRepository.save(payment));
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .map(PaymentResponse::from)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }
}
