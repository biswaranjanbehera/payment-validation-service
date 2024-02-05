package com.cpt.payments.controller;

import com.cpt.payments.constant.Endpoint;
import com.cpt.payments.constant.ValidatorEnum;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.service.Validator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(Endpoint.VALIDATION_MAPPING)
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    ApplicationContext context;

    @PostMapping(value = Endpoint.INITIATE_PAYMENT)
    public ResponseEntity<PaymentResponse> sale(@RequestBody PaymentRequest paymentRequest) {
        /*LogMessage.setLogMessagePrefix("/INITIATE_PAYMENT");
        LogMessage.log(LOGGER, " initiate payment request " + paymentRequest);
        */

        String validationRules = "PROVIDER_ID_FILTER,PROVIDER_ID_FILTER";
        List<String> validatorList = Stream.of(validationRules.split(",")).collect(Collectors.toList());

        validatorList.forEach(validator->{
            ValidatorEnum validatorEnum = ValidatorEnum.getEnumByName(validator);
            Validator validationRule = context.getBean(validatorEnum.getValidatorClass());
            validationRule.doValidate(paymentRequest);
        });



        System.out.println("Invoking sale method");
        return new ResponseEntity<>(paymentService.validateAndInitiatePayment(paymentRequest), HttpStatus.CREATED);
    }

}
