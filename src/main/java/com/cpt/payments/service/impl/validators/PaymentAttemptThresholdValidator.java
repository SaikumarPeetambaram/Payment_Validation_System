package com.cpt.payments.service.impl.validators;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ValidatorEnum;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import com.cpt.payments.service.impl.RedisService;

@Component
public class PaymentAttemptThresholdValidator implements Validator {

	@Autowired
	private RedisService redisService;
	
	private static final Logger LOGGER = LogManager.getLogger(
			PaymentAttemptThresholdValidator.class);

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		LOGGER.info("Executing PaymentAttemptThresholdValidator:{}", paymentRequest);
		
		Map<String, String> params = redisService.getAllEntriesFromHash(
				ValidatorEnum.PAYMENT_ATTEMPT_THRESHOLD_FILTER.getValidatorName());
		
		int noOfHrs = Integer.parseInt(params.get("noOfHrs"));
		
		//TODO business log
		LOGGER.info("noOfHrs:{}", noOfHrs);
	}
}
