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
public class ProviderIdValidatorTest {
	
	@InjectMocks
	ProviderIdValidator validator;
	
	@Test
	void testDoValidateInCorrectProviderID() {
		
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setProviderId("Trustly-TEMP");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidateValidProviderId() {
		
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
	void testDoValidateProviderIdCaseInsensitive() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setProviderId("TrUstLY");
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
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(), 
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
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidateProviderIdWithLeadTailSpace() {
		
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setProviderId("  Trustly  ");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		assertDoesNotThrow(
				() -> validator.doValidate(paymentRequest)
		);
	}
	
	@Test
	void testDoValidateNullProviderId() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setProviderId(null);
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidateEmptyProviderId() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setProviderId("");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	void testDoValidateBlankProviderId() {
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		payment.setProviderId("      ");
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> validator.doValidate(paymentRequest));
		
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.PROVIDER_ID_VALIDATION_FAILED.getErrorMessage(), 
				returnedException.getErrorMessage());
	}

}

