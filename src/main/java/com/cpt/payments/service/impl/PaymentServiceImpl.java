package com.cpt.payments.service.impl;

import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentResponse validateAndInitiatePayment(PaymentRequest paymentRequest) {
        System.out.println("Invoking service method  - validateand Initiate Payment");

        //Todo replace with actual trustly integration code
        PaymentResponse res = new PaymentResponse();
        res.setPaymentReference("Payment-reference");
        res.setRedirectUrl("trudtly-url");
        return res;
    }
}
