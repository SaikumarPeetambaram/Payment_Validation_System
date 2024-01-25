package com.cpt.payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constants.Endpoint;
import com.cpt.payments.service.PaymentService;

@RestController
@RequestMapping(Endpoint.VALIDATION_MAPPING)
public class PaymentsController {
	
	@Autowired
	private PaymentService paymentService;

	@PostMapping(value = Endpoint.INITIATE_PAYMENT)
	public ResponseEntity<String> sale(@RequestBody String paymentRequest) {
		/*
		 * LogMessage.setLogMessagePrefix("/INITIATE_PAYMENT"); LogMessage.log(LOGGER,
		 * " initiate payment request " + paymentRequest);
		 */
		System.out.println("Invoking sale method");

		return new ResponseEntity<>(
				paymentService.validateAndInitiatePayment(paymentRequest), 
				HttpStatus.CREATED);
	}
}