package com.cpt.payments.constants;

import com.cpt.payments.service.Validator;
import com.cpt.payments.service.impl.validators.PaymentTypeValidator;
import com.cpt.payments.service.impl.validators.ProviderIdValidator;

import lombok.Getter;

public enum ValidatorEnum {
	
	PAYMENT_TYPE_FILTER("PAYMENT_TYPE_FILTER", PaymentTypeValidator.class),
	PROVIDER_ID_FILTER("PROVIDER_ID_FILTER", ProviderIdValidator.class);
	
	@Getter
	private String validatorName;

	@Getter
	private Class<? extends Validator> validatorClass;

	private ValidatorEnum(String validatorName, Class<? extends Validator> validatorClass) {
		this.validatorName = validatorName;
		this.validatorClass = validatorClass;
	}
	
	public static ValidatorEnum getEnumByName(String name) {
		for (ValidatorEnum e : ValidatorEnum.values()) {
			if (name.equals(e.validatorName))
				return e;
		}
		return null;
	}
}
