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
public class PaymentMethodValidatorTest {
	
	@InjectMocks
	PaymentMethodValidator validator;
	
	@Test
	void testDoValidateInCorrectPamentType() {
		
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentMethod("APM-TEMP");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidateValidPaymentMethod() {
		
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
	void testDoValidatePaymentMethodCaseInsensitive() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentMethod("ApM");
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
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorMessage(), 
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
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidatePaymentMethodWithLeadTailSpace() {
		
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentMethod("  APM  ");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		assertDoesNotThrow(
				() -> validator.doValidate(paymentRequest)
		);
	}
	
	@Test
	void testDoValidateNullPaymentMethod() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentMethod(null);
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidateEmptyPaymentMethod() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentMethod("");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidateBlankPaymentMethod() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setPaymentMethod("      ");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PAYMENT_METHOD_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}

}
