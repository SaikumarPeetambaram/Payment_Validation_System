package com.cpt.payments.service.impl.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import com.cpt.payments.service.impl.PaymentServiceImpl;

@Component
public class ProviderIdValidator implements Validator{
	
	private static final Logger LOGGER = LogManager.getLogger(ProviderIdValidator.class);

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LOGGER.info("Validating paymentRequest:{}", paymentRequest);
		// TODO Auto-generated method stub
		
		String providerId = paymentRequest.getPayment()
				.getProviderId().trim();
		if(!providerId.equalsIgnoreCase("Trustly")) {
			//TODO raise an exception
			LOGGER.warn("ProviderIdValidator is NOT VALID");
			
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(),
					ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage());
			
		}else {
			LOGGER.info("ProviderIdValidator is VALID");
		}
		
	}

}
