package com.cpt.payments.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constants.Endpoint;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.service.HmacSha256Provider;
import com.cpt.payments.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Endpoint.VALIDATION_MAPPING)
public class PaymentsController {

	private static final Logger LOGGER = LogManager.getLogger(PaymentsController.class);

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private HmacSha256Provider hmacSha256Provider;

	@PostMapping(value = Endpoint.INITIATE_PAYMENT)
	public ResponseEntity<PaymentResponse> sale(
			@RequestBody PaymentRequest paymentRequest) {

		LOGGER.info("Initiate paymentRequest:{}", 
				paymentRequest);

		PaymentResponse serviceResponse = paymentService.validateAndInitiatePayment(paymentRequest);
		
		ResponseEntity<PaymentResponse> paymentResponse = new ResponseEntity<>(
				serviceResponse, 
				HttpStatus.CREATED);
		
		LOGGER.info("Returning payment response {}", paymentResponse);
		
		return paymentResponse;
	}
	
	@PostMapping(value = Endpoint.PROCESS_PAYMENT)
	public String processPayment(
			@RequestBody PaymentRequest paymentRequest,
			HttpServletRequest request) {

		LOGGER.info("Invoking processPayment paymentRequest:{}", 
				paymentRequest);
		
		return "Invoked processPayment method";
	}
	

	private void checkSigAndExitWhenInvalid(String requestSignature, String requestDataAsJson) {
		hmacSha256Provider.isSigValid(
				requestDataAsJson, requestSignature);
	}

}