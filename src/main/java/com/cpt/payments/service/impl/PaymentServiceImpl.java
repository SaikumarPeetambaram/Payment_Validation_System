package com.cpt.payments.service.impl;

import org.springframework.stereotype.Service;

import com.cpt.payments.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
	public String validateAndInitiatePayment(String paymentRequest) {
		// TODO Auto-generated method stub
		
		System.out.println("Invoking service method - validateAndInitiatePayment || paymentRequest:" + paymentRequest);
		return paymentRequest;
	}

}
