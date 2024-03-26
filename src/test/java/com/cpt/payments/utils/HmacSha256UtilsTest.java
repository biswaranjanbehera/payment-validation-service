package com.cpt.payments.utils;

import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.User;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HmacSha256UtilsTest {

    private Logger LOGGER = LogManager.getLogger(HmacSha256UtilsTest.class);

    @InjectMocks
    HmacSha256Utils hmacSha256Utils;

    @Test
    void testCalculateHmacSuccess(){
        //Arrange data
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        Gson gson = new Gson();
        String requestData = gson.toJson(paymentRequest);
        String secretKey = "ecom-123qwe!@#";

        //Invoke the method
        String generatedSignature  = hmacSha256Utils.calculateHmac(secretKey, requestData);
        LOGGER.info("generatedSignature:- {}",generatedSignature);

        //verify what you expect to happen from method
        Assertions.assertNotNull(generatedSignature);
        Assertions.assertNotEquals("",generatedSignature);
    }

    @Test
    void testVerifyHmacSuccess(){
        //Arrange data
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        Gson gson = new Gson();
        String requestData = gson.toJson(paymentRequest);
        String secretKey = "ecom-123qwe!@#";

        //Invoke the method
        String recievedHmac = "H9D+RC5qjynDjo3LJVsJhxKqshH+z1mMBXeEe3oVxpE=";
        boolean isSigValid  = hmacSha256Utils.verify(secretKey, requestData, recievedHmac);

        //verify what you expect to happen from method
        Assertions.assertTrue(isSigValid);
    }
}
