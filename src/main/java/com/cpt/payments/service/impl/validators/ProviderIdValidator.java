package com.cpt.payments.service.impl.validators;


import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import org.springframework.stereotype.Component;

@Component
public class ProviderIdValidator implements Validator {


	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		//todo
		boolean isError = false;
		if(isError){
			//todo raise an exception
			System.out.println("Providevalidatior is invalid");
		}else{
			System.out.println("Providevalidatior is valid");

		}

	}
}
