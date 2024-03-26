package com.cpt.payments.controller;

import com.cpt.payments.constant.Endpoint;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.service.HmacSha256Provider;
import com.cpt.payments.service.PaymentService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.VALIDATION_MAPPING)
public class PaymentController {

    private static final Logger LOGGER = LogManager.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private HmacSha256Provider hmacSha256Provider;


    @PostMapping(value = Endpoint.INITIATE_PAYMENT)
    public ResponseEntity<PaymentResponse> sale(@RequestBody PaymentRequest paymentRequest,
                                                HttpServletRequest httpServletRequest)  {


        String requestSignature = httpServletRequest.getHeader("signature");

        LOGGER.debug("Invoking sale method");
        LOGGER.info("initiate paymentRequest {} | signature:{}",paymentRequest,requestSignature);

        Gson gson = new Gson();
        String requestDataAsJson = gson.toJson(paymentRequest);

        /*this method throws exception when sig is invalid and this flow will break
        Only when sig is valid this flow will continue, and process the service API call
         */

        checkSignatureAndExitWhenInvalid(requestDataAsJson, requestSignature);


        LOGGER.info("SIGNATURE IS VALID continue processing the payment");
        return new ResponseEntity<>(paymentService.validateAndInitiatePayment(paymentRequest), HttpStatus.CREATED);
    }

    private void checkSignatureAndExitWhenInvalid(String requestDataAsJson, String requestSignature){
        hmacSha256Provider.isSigValid(requestDataAsJson,requestSignature);
    }

}

  /*LOGGER.trace();
        LOGGER.debug();
        LOGGER.info();
        LOGGER.warn();
        LOGGER.error();
        LOGGER.fatal();*/
