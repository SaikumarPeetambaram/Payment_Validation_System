package com.cpt.payments.service.impl.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;

@Component
public class PaymentMethodValidator implements Validator{

	private static final Logger LOGGER = LogManager.getLogger(PaymentMethodValidator.class);

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LOGGER.info("Validating paymentRequest:{}", paymentRequest);
		// TODO Auto-generated method stub
		
		if(paymentRequest != null
				&& paymentRequest.getPayment() != null
				&& paymentRequest.getPayment().getPaymentMethod() != null) {
			//trimmed PaymentMethod value
			String paymentMethod = paymentRequest.getPayment()
					.getPaymentMethod().trim();
			if(paymentMethod.equalsIgnoreCase("APM")) {
				//Request is valid
				LOGGER.info("paymentMethod Valid");
				return;
			}else {
				LOGGER.info("Payment PaymentMethod is not APM paymentMethod:{}", paymentMethod);
			}
		}else {
			LOGGER.info("Payment PaymentMethod is Null - INVALID");
		}
		LOGGER.info("Payment PaymentMethod is INVALID, throwing exception");
		throw new ValidationException(HttpStatus.BAD_REQUEST,
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorCode(),
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorMessage());
	}

}
