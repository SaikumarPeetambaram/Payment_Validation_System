package com.cpt.payments.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.pojo.User;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.utils.TestDataProviderUtil;

@ExtendWith(MockitoExtension.class)
public class PaymentsControllerTest {

	@Mock
	private PaymentService paymentService;
	
	@InjectMocks
	PaymentsController controller;
	
	@Test
	void testSale() {
		
		PaymentRequest paymentRequest = new PaymentRequest();
		Payment payment = TestDataProviderUtil.getTestPayment();
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setPaymentReference("My test Reference");
		paymentResponse.setRedirectUrl("My test redirect url");

		// Defining the mock behaviour
		when(paymentService.validateAndInitiatePayment(any()))
		.thenReturn(paymentResponse);
		
		ResponseEntity<PaymentResponse> saleResponse = controller.sale(paymentRequest);
		
		assertNotNull(saleResponse);
		assertNotNull(saleResponse.getBody());
		assertEquals(HttpStatus.CREATED, saleResponse.getStatusCode());
		
		assertEquals(paymentResponse.getPaymentReference(), saleResponse.getBody().getPaymentReference());
		
	}
}

