package com.cpt.payments.service.impl.validators;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.User;
import com.cpt.payments.utils.TestDataProviderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PaymentTypeValidatorTest {

    @InjectMocks
    PaymentTypeValidator paymentTypeValidator;
    @Test
    void testDoValidateIncorrectPaymentType(){
        PaymentRequest paymentRequest = new PaymentRequest();
        Payment payment = new Payment();
        payment.setAmount("18.00");
        payment.setCreditorAccount("4434343");
        payment.setCurrency("EUR");
        payment.setDebitorAccount("name");
        payment.setMerchantTransactionReference("CPT");
        payment.setPaymentMethod("APM");
        payment.setPaymentType("SALE-Temp");
        payment.setProviderId("Trustly");

        paymentRequest.setPayment(payment);

        User user = new User();
        user.setEmail("johnpeter@gmail.com");
        user.setFirstName("john");
        user.setLastName("peter");
        user.setPhoneNumber("+91939393939");
        paymentRequest.setUser(user);

        //providerIdValidator.doValidate(paymentRequest);

        ValidationException returnedException = assertThrows(ValidationException.class,
                ()-> paymentTypeValidator.doValidate(paymentRequest));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());

    }

    @Test
    void testDoValidateValidPaymentType(){
        PaymentRequest paymentRequest = new PaymentRequest();
        Payment payment = new Payment();
        payment.setAmount("18.00");
        payment.setCreditorAccount("4434343");
        payment.setCurrency("EUR");
        payment.setDebitorAccount("name");
        payment.setMerchantTransactionReference("CPT");
        payment.setPaymentMethod("APM");
        payment.setPaymentType("SALE");
        payment.setProviderId("Trustly");

        paymentRequest.setPayment(payment);

        User user = new User();
        user.setEmail("johnpeter@gmail.com");
        user.setFirstName("john");
        user.setLastName("peter");
        user.setPhoneNumber("+91939393939");
        paymentRequest.setUser(user);

        //providerIdValidator.doValidate(paymentRequest);

        Assertions.assertDoesNotThrow(()-> paymentTypeValidator.doValidate(paymentRequest));

    }

    @Test
    void testDoValidatePaymentTypeCaseInsensitive(){
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        payment.setPaymentType("sAlE");
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        Assertions.assertDoesNotThrow(()-> paymentTypeValidator.doValidate(paymentRequest));

    }

    @Test
    void testDoValidatePaymentRequestNull(){
        PaymentRequest paymentRequest = null;

        ValidationException returnedException = assertThrows(ValidationException.class,
                ()-> paymentTypeValidator.doValidate(paymentRequest));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }

    @Test
    void testDoValidatePaymentIsNull(){
        PaymentRequest paymentRequest = new PaymentRequest();
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setUser(user);
        paymentRequest.setPayment(null);


        ValidationException returnedException = assertThrows(ValidationException.class,
                ()-> paymentTypeValidator.doValidate(paymentRequest));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }

    @Test
    void testDoValidatePaymentTypeWithLeadTrailSpaces(){
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        payment.setPaymentType("   SALE    ");
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        Assertions.assertDoesNotThrow(()-> paymentTypeValidator.doValidate(paymentRequest));

    }

    @Test
    void testDoValidateNullPaymentType(){
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        payment.setPaymentType(null);
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        ValidationException returnedException = assertThrows(ValidationException.class,
                ()-> paymentTypeValidator.doValidate(paymentRequest));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }

    @Test
    void testDoValidateBlankPaymentType(){
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        payment.setPaymentType("");
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        ValidationException returnedException = assertThrows(ValidationException.class,
                ()-> paymentTypeValidator.doValidate(paymentRequest));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }


}
