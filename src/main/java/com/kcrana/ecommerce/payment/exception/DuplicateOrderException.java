package com.kcrana.ecommerce.payment.exception;

public class DuplicateOrderException extends RuntimeException {

    public DuplicateOrderException(String orderId) {
        super("A payment already exists for order: " + orderId);
    }
}
