package com.cpt.payments.service.impl.validators;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.User;
import com.cpt.payments.service.impl.ProviderIdValidator;
import com.cpt.payments.utils.TestDataProviderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class ProviderIdValidatorTest {

    @InjectMocks
    ProviderIdValidator providerIdValidator;
    @Test
    void testDoValidateIncorrectProviderID(){
        PaymentRequest paymentRequest = new PaymentRequest();
        Payment payment = new Payment();
        payment.setAmount("18.00");
        payment.setCreditorAccount("4434343");
        payment.setCurrency("EUR");
        payment.setDebitorAccount("name");
        payment.setMerchantTransactionReference("CPT");
        payment.setPaymentMethod("APM");
        payment.setPaymentType("SALE");
        payment.setProviderId("Trustly-Temp");

        paymentRequest.setPayment(payment);

        User user = new User();
        user.setEmail("johnpeter@gmail.com");
        user.setFirstName("john");
        user.setLastName("peter");
        user.setPhoneNumber("+91939393939");
        paymentRequest.setUser(user);

        //providerIdValidator.doValidate(paymentRequest);

        ValidationException returnedException = assertThrows(ValidationException.class,
                ()-> providerIdValidator.doValidate(paymentRequest));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());

    }

    @Test
    void testDoValidateValidProviderID(){
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

        Assertions.assertDoesNotThrow(()-> providerIdValidator.doValidate(paymentRequest));

    }

    @Test
    void testDoValidateProviderIDCaseInsensitive(){
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        payment.setProviderId("TrUstlY");
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        Assertions.assertDoesNotThrow(()-> providerIdValidator.doValidate(paymentRequest));

    }

    @Test
    void testDoValidatePaymentRequestNull(){
        PaymentRequest paymentRequest = null;

        ValidationException returnedException = assertThrows(ValidationException.class,
                ()-> providerIdValidator.doValidate(paymentRequest));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }

    @Test
    void testDoValidatePaymentIsNull(){
        PaymentRequest paymentRequest = new PaymentRequest();
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setUser(user);
        paymentRequest.setPayment(null);


        ValidationException returnedException = assertThrows(ValidationException.class,
                ()-> providerIdValidator.doValidate(paymentRequest));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }

    @Test
    void testDoValidateProviderIDWithLeadTrailSpaces(){
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        payment.setProviderId("   TrUstlY    ");
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        Assertions.assertDoesNotThrow(()-> providerIdValidator.doValidate(paymentRequest));

    }

    @Test
    void testDoValidateNullProviderID(){
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        payment.setProviderId(null);
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        ValidationException returnedException = assertThrows(ValidationException.class,
                ()-> providerIdValidator.doValidate(paymentRequest));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }

    @Test
    void testDoValidateBlankProviderID(){
        PaymentRequest paymentRequest = new PaymentRequest();

        Payment payment = TestDataProviderUtil.getTestPayment();
        payment.setProviderId("");
        User user = TestDataProviderUtil.getTestUserBean();

        paymentRequest.setPayment(payment);
        paymentRequest.setUser(user);

        ValidationException returnedException = assertThrows(ValidationException.class,
                ()-> providerIdValidator.doValidate(paymentRequest));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(),
                returnedException.getErrorMessage());
    }



}
