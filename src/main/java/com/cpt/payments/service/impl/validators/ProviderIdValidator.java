package com.cpt.payments.service.impl.validators;

import org.springframework.stereotype.Component;

import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;

@Component
public class ProviderIdValidator implements Validator{

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		// TODO Auto-generated method stub
		
		boolean isError = false;
		if(isError) {
			//TODO raise an exception
			System.out.println("ProviderIdValidator is NOT VALID");
		}else {
			System.out.println("ProviderIdValidator is VALID");
		}
		
	}

}
