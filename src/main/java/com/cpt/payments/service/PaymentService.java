package com.cpt.payments.service;

public interface PaymentService {

    PaymentResponse validateAndInitiatePayment(PaymentRequest paymentRequest);

}
