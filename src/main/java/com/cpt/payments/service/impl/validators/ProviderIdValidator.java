package com.cpt.payments.service.impl.validators;


import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ProviderIdValidator implements Validator {


	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		//todo
		boolean isError = false;
		if(!paymentRequest.getPayment().getProviderId().equalsIgnoreCase("Trustly")){
			System.out.println("Providevalidatior is invalid");
			//todo raise an exception
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage());

		}else{
			System.out.println("Providevalidatior is valid");

		}

	}
}
