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
import com.cpt.payments.service.PaymentService;

@RestController
@RequestMapping(Endpoint.VALIDATION_MAPPING)
public class PaymentsController {
	
	private static final Logger LOGGER = LogManager.getLogger(PaymentsController.class);

	@Autowired
	private PaymentService paymentService;

	@PostMapping(value = Endpoint.INITIATE_PAYMENT)
	public ResponseEntity<PaymentResponse> sale(//TODO change String to actual Request structure
			@RequestBody PaymentRequest paymentRequest) {
		
		LOGGER.info("Initiate payment request:{}", paymentRequest);
		
		PaymentResponse serviceResponse = paymentService.validateAndInitiatePayment(paymentRequest);
		
		ResponseEntity<PaymentResponse> paymentResponse = new ResponseEntity<>(
				serviceResponse, 
				HttpStatus.CREATED);
		
		LOGGER.info("Returning payment response:{}", paymentResponse);
		
		return paymentResponse;

	}
}