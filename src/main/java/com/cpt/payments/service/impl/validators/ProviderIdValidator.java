package com.cpt.payments.service.impl.validators;


import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ProviderIdValidator implements Validator {

	private Logger LOGGER = LogManager.getLogger(ProviderIdValidator.class);


	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LOGGER.info("Validating PaymentRequest:- {}",paymentRequest);
		//todo
		boolean isError = false;

		String providerId = paymentRequest.getPayment().getProviderId().trim();
		if(!providerId.equalsIgnoreCase("Trustly")){
			LOGGER.warn("Providevalidatior is invalid");
			//todo raise an exception
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage());

		}else{
			LOGGER.info("Providevalidatior is valid");

		}

	}
}
