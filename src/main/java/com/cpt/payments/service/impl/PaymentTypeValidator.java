package com.cpt.payments.service.impl;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentTypeValidator implements Validator {

    private Logger LOGGER = LogManager.getLogger(PaymentTypeValidator.class);

    @Override
    public void doValidate(PaymentRequest paymentRequest) {
        LOGGER.info("Validating PaymentRequest:- {}",paymentRequest);
        if(paymentRequest!=null
                && paymentRequest.getPayment()!=null
                && paymentRequest.getPayment().getPaymentType()!=null){

            //trimmed paymetType
            String paymetType = paymentRequest.getPayment().getPaymentType().trim();
            if(paymetType.equalsIgnoreCase("SALE")){
                LOGGER.info("Payment type is valid");
                return;
            }else{
                LOGGER.info("Payment type is not SALE paymetType:{}",paymetType);
            }
        }else{
            LOGGER.info("Payment type is NULL- INVALID");
        }

        LOGGER.info("Payment type Is INVALID throwing exception");

        throw new ValidationException(HttpStatus.BAD_REQUEST,
                ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(),
                ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage());

    }
}
