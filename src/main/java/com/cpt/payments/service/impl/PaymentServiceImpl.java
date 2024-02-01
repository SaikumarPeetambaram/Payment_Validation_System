package com.cpt.payments.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.ValidatorEnum;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.service.Supplier;
import com.cpt.payments.service.Validator;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Value("${payment.validators}")
	private String validationRules;

	@Override
	public PaymentResponse validateAndInitiatePayment(PaymentRequest paymentRequest) {
		// TODO Auto-generated method stub
		
		LOGGER.info("Invoking service method - validateAndInitiatePayment || paymentRequest:" + paymentRequest + "|validationRules" + validationRules);
		
		List<String> validatorList = Stream.of(validationRules.split(",")).collect(Collectors.toList());

		validatorList.forEach(validator -> {

			ValidatorEnum validatorEnum = ValidatorEnum.getEnumByName(validator);
			
			Supplier<? extends Validator> validatorSupplier = () -> context.getBean(validatorEnum.getValidatorClass());			
			validatorSupplier.get().doValidate(paymentRequest);
		});
		
		//TODO replace with actual trustly integration code
		PaymentResponse res = new PaymentResponse();
		res.setPaymentReference("payment-reference");
		res.setRedirectUrl("trustly-url");
		return res;
	}

}
