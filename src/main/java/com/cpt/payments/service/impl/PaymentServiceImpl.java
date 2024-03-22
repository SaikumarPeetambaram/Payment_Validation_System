package com.cpt.payments.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.Constants;
import com.cpt.payments.constants.ValidatorEnum;
import com.cpt.payments.dao.ValidatorDao;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.service.Supplier;
import com.cpt.payments.service.Validator;

import jakarta.annotation.PostConstruct;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

	@Autowired
	private ApplicationContext context;

	@Value("${payment.validators}")
	private String validationRules;

	@Autowired
	private RedisService redisService;

	@Autowired
	private ValidatorDao validatorDao;

	@Override
	public PaymentResponse validateAndInitiatePayment(PaymentRequest paymentRequest) {
		LOGGER.info("Invoking service method - validateAndInitiatePayment||paymentRequest:{}|validationRules:{}", paymentRequest, validationRules);

		List<String> validatorList = redisService.getAllValuesFromList(Constants.validatorRulesKeyInRedis);

		if(validatorList == null || validatorList.isEmpty()) {
			validatorList = loadValidatorsFromDB(); 
		}

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

	// Your method to run after successful bean initialization
	// This method will be invoked automatically after the bean is constructed
	// and all dependencies are injected
	@PostConstruct
	public void initialize() {
		List<String> validatorList = redisService.getAllValuesFromList(Constants.validatorRulesKeyInRedis);

		if(validatorList == null || validatorList.isEmpty()) {	
			loadValidatorsFromDB();
			LOGGER.debug("validator loaded on startup");
		} else {
			LOGGER.debug("validator already in cache");
		}
	}

	public List<String> loadValidatorsFromDB() {
		List<String> validatorList;
		LOGGER.debug("validatorList not in Redis, loading from DB");

		validatorList = validatorDao.getActiveValidators();
		redisService.addValuesToList(Constants.validatorRulesKeyInRedis, validatorList);

		//Along with Validator rules, get Validator params & store it in redis.

		validatorList.forEach(validatorName -> {

			StringBuilder addValidatorParams = new StringBuilder();
			Map<String, String> validatorParams = validatorDao.getValidatorParams(validatorName);
			if(validatorParams != null && !validatorParams.isEmpty()) {
				redisService.setBulkValueInHash(validatorName, validatorParams);
				addValidatorParams.append("," + validatorName);
			}

			LOGGER.info("Stored params in cache for validators:{}", addValidatorParams);
		});

		LOGGER.info("validatorList loaded from DB & saved in Redis validatorList:{}", validatorList);
		return validatorList;
	}
}
