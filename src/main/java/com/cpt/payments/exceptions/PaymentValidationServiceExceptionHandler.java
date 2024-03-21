package com.cpt.payments.exceptions;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.pojo.PaymentError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PaymentValidationServiceExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<PaymentError> handleValidationException(ValidationException ex) {
        //LogMessage.log(LOGGER, " validation exception is -> " + ex.getErrorMessage());
        PaymentError paymentResponse = PaymentError.builder()
                                        .errorCode(ex.getErrorCode())
                                        .errorMessage(ex.getErrorMessage()).build();
        //LogMessage.log(LOGGER, " paymentResponse is -> " + paymentResponse);
        return new ResponseEntity<>(paymentResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PaymentError> handleGenericException(Exception ex) {
        //LogMessage.log(LOGGER, " generic exception message is -> " + ex.getMessage());
        //LogMessage.logException(LOGGER, ex);
        PaymentError paymentResponse = PaymentError.builder()
                .errorCode(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode())
                .errorMessage(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage())
                .build();
        //LogMessage.log(LOGGER, " paymentResponse is -> " + paymentResponse);
        return new ResponseEntity<>(paymentResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
