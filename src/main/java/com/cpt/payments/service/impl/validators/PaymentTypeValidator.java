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
public class PaymentTypeValidator implements Validator {

	private static final Logger LOGGER = LogManager.getLogger(PaymentTypeValidator.class);

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LOGGER.info("Validating paymentRequest:{}", paymentRequest);
		// TODO Auto-generated method stub
		
		if(paymentRequest != null
				&& paymentRequest.getPayment() != null
				&& paymentRequest.getPayment().getPaymentType() != null) {
			//trimmed PaymentType value
			String paymentType = paymentRequest.getPayment()
					.getPaymentType().trim();
			if(paymentType.equalsIgnoreCase("SALE")) {
				//Request is valid
				LOGGER.info("paymentType Valid");
				return;
			}else {
				LOGGER.info("Payment PaymentType is not SALE paymentType:{}", paymentType);
			}
		}else {
			LOGGER.info("Payment PaymentType is Null - INVALID");
		}
		LOGGER.info("Payment PaymentType is INVALID, throwing exception");
		throw new ValidationException(HttpStatus.BAD_REQUEST,
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(),
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage());
	}

}
