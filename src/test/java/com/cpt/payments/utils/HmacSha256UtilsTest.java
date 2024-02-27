package com.cpt.payments.utils;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cpt.payments.controller.PaymentsController;
import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.User;
import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
public class HmacSha256UtilsTest {
	
	private static final Logger LOGGER = LogManager.getLogger(HmacSha256UtilsTest.class);
	
	@InjectMocks
	HmacSha256Utils hmacSha256Utils;
	
	@Test
	void testCalculateHmacSuccess() {
		//Arrange data
		String secretKey = "ecom-123qwe!@#";
		
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		Gson gson = new Gson();
		String requestData = gson.toJson(paymentRequest);
		
		
		//Invoke the method
		String generatedSignature = hmacSha256Utils.calculateHmac(secretKey, requestData);
		LOGGER.info("generatedSignature: {}", generatedSignature);
		
		
		//verify what you expect to happen from method
		assertNotNull(generatedSignature);
		assertNotEquals("", generatedSignature);
	}
	
	@Test
	void testVerifyHmac() {
		//Arrange data
		String secretKey = "ecom-123qwe!@#";
		
		PaymentRequest paymentRequest = new PaymentRequest();

		Payment payment = TestDataProviderUtil.getTestPayment();
		User user = TestDataProviderUtil.getTestUserBean();
		
		paymentRequest.setPayment(payment);
		paymentRequest.setUser(user);
		
		Gson gson = new Gson();
		String requestData = gson.toJson(paymentRequest);
		
		
		//Invoke the method
		String receivedHmac = "H9D+RC5qjynDjo3LJVsJhxKqshH+z1mMBXeEe3oVxpE=";
		boolean isSigValid = hmacSha256Utils.verifyHmac(secretKey, requestData, receivedHmac);
		//LOGGER.info("generatedSignature: {}", generatedSignature);
		
		//verify what you expect to happen from method
		assertTrue(isSigValid);
	}
}
