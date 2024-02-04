package com.cpt.payments.service.impl;

import com.cpt.payments.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentResponse validateAndInitiatePayment(PaymentRequest paymentRequest) {
        System.out.println("Invoking service method  - validateand Initiate Payment");
        return null;
    }
}
