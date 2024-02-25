package com.cpt.payments.service.impl.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.User;
import com.cpt.payments.utils.TestDataProviderUtil;

@ExtendWith(MockitoExtension.class)
public class PaymentTypeValidatorTest {
	
	@InjectMocks
	PaymentTypeValidator validator;
	
	@Test
	void testDoValidateInCorrectPamentType() {
		
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentType("SALE-TEMP");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidateValidPaymentType() {
		
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		assertDoesNotThrow(
				() -> validator.doValidate(paymentRequest)
		);
	}
	
	
	@Test
	void testDoValidatePaymentTypeCaseInsensitive() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentType("SaLe");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		assertDoesNotThrow(
				() -> validator.doValidate(paymentRequest)
		);
	}
	
	@Test
	void testDoValidatePamentRequestNull() {
		PaymentRequest paymentRequest = null;
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidatePamentIsNull() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = null;
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidatePaymentTypeWithLeadTailSpace() {
		
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentType("  SALE  ");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		assertDoesNotThrow(
				() -> validator.doValidate(paymentRequest)
		);
	}
	
	@Test
	void testDoValidateNullPaymentType() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentType(null);
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidateEmptyPaymentType() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentType("");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidateBlankPaymentType() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentType("      ");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_TYPE_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}

}

