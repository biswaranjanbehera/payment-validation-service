package com.cpt.payments.controller;


import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.pojo.User;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.utils.TestDataProviderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    PaymentController paymentController;

    @Test
    void testSale(){
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentReference("My test reference");
        paymentResponse.setRedirectUrl("my redirect url");

        //Defining the Mock behaviour
        when(paymentService.validateAndInitiatePayment(any())).thenReturn(paymentResponse);

        ResponseEntity<PaymentResponse> saleResponse = paymentController.sale(paymentRequest);
        Assertions.assertNotNull(saleResponse);
        Assertions.assertEquals(HttpStatus.CREATED,saleResponse.getStatusCode());

        Assertions.assertEquals(paymentResponse.getPaymentReference(),saleResponse.getBody().getPaymentReference());
    }
}
