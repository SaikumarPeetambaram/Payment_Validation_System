package com.cpt.payments.dao;

import java.util.List;
import java.util.Map;

public interface ValidatorDao {
	
	public List<String> getActiveValidators();
	
	public Map<String, String> getValidatorParams(String validatorName);

}
