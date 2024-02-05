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
		//TODO write all testcase code.
		//CODE which protects functional code
		
		PaymentRequest paymentRequest = new PaymentRequest();
		Payment payment = new Payment();
		payment.setAmount("18.00");
		payment.setCreditorAccount("4242424242424242");
		payment.setCurrency("EUR");
		payment.setDebitorAccount("4111111111111111");
		payment.setMerchantTransactionReference("cptraining_test201");
		payment.setPaymentMethod("APM");
		payment.setPaymentType("SALE");
		payment.setProviderId("Trustly-TEMP");
		
		paymentRequest.setPayment(payment);
		
		User user = new User();
		user.setEmail("johnpeter@gmail.com");
		user.setFirstName("john");
		user.setLastName("peter");
		user.setPhoneNumber("+919393939393");
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
		//TODO write all testcase code.
		//CODE which protects functional code
		
		PaymentRequest paymentRequest = new PaymentRequest();
		Payment payment = new Payment();
		payment.setAmount("18.00");
		payment.setCreditorAccount("4242424242424242");
		payment.setCurrency("EUR");
		payment.setDebitorAccount("4111111111111111");
		payment.setMerchantTransactionReference("cptraining_test201");
		payment.setPaymentMethod("APM");
		payment.setPaymentType("SALE");
		payment.setProviderId("Trustly");
		
		paymentRequest.setPayment(payment);
		
		User user = new User();
		user.setEmail("johnpeter@gmail.com");
		user.setFirstName("john");
		user.setLastName("peter");
		user.setPhoneNumber("+919393939393");
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

}

