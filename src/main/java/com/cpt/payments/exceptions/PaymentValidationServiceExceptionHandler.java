package com.cpt.payments.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.pojo.PaymentError;

@ControllerAdvice
public class PaymentValidationServiceExceptionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(PaymentValidationServiceExceptionHandler.class);
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<PaymentError> handleValidationException(ValidationException ex) {
		LOGGER.debug("validation exception is :{}",  ex.getErrorMessage());
		PaymentError paymentResponse = PaymentError.builder()
				.errorCode(ex.getErrorCode())
				.errorMessage(ex.getErrorMessage())
				.build();
		LOGGER.info("paymentResponse is:{}",  paymentResponse);
		return new ResponseEntity<>(paymentResponse, ex.getHttpStatus());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<PaymentError> handleGenericException(Exception ex) {
		LOGGER.info("generic exception message is:{}",  ex.getMessage());
		PaymentError paymentResponse = PaymentError.builder()
				.errorCode(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode())
				.errorMessage(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage())
				.build();
		LOGGER.info("paymentResponse is :{}",  paymentResponse);
		return new ResponseEntity<>(paymentResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
