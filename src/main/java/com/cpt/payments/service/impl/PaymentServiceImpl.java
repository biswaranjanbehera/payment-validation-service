package com.cpt.payments.service.impl;

import com.cpt.payments.constant.ValidatorEnum;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.service.Supplier;
import com.cpt.payments.service.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ApplicationContext context;

    @Value("${payment.validators}")
    private String validationRules;

    @Override
    public PaymentResponse validateAndInitiatePayment(PaymentRequest paymentRequest) {
        System.out.println("Invoking service method  - validateand Initiate Payment || paymentrequest:- "+paymentRequest);


        //String validationRules = "PROVIDER_ID_FILTER,PROVIDER_ID_FILTER";
        List<String> validatorList = Stream.of(validationRules.split(",")).collect(Collectors.toList());

        validatorList.forEach(validator->{
            ValidatorEnum validatorEnum = ValidatorEnum.getEnumByName(validator);
            Supplier<? extends Validator> validatorSupplier = () -> context.getBean(validatorEnum.getValidatorClass());
            validatorSupplier.get().doValidate(paymentRequest);

            /*Validator validationRule = context.getBean(validatorEnum.getValidatorClass());
            validationRule.doValidate(paymentRequest);*/
        });


        //Todo replace with actual trustly integration code
        PaymentResponse res = new PaymentResponse();
        res.setPaymentReference("Payment-reference");
        res.setRedirectUrl("trudtly-url");
        return res;
    }
}
