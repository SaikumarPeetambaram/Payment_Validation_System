package com.cpt.payments.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.ValidatorDao;

@Repository
public class ValidatorDaoImpl implements ValidatorDao {

	private static final Logger LOGGER = LogManager.getLogger(ValidatorDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public List<String> getActiveValidators() {
		LOGGER.info(" :: getActiveValidators ordered by priority");

		List<String> validatorList = null;
		SqlParameterSource src = null;
		try {
			List<Map<String, Object>> rows = namedJdbcTemplate.queryForList(
					generateGetValidatorQuery(), src);
			
			// Extract validatorName values from the result set
		    validatorList = new ArrayList<>();
		    for (Map<String, Object> row : rows) {
		        validatorList.add((String) row.get("validatorName"));
		    }
			LOGGER.info(" :: getActiveValidators validatorList from DB: {}", validatorList);
		} catch (Exception e) {
			LOGGER.error("unable to get MerchantPaymentRequest Details " + e);
		}
		return validatorList;
	}
	
	private String generateGetValidatorQuery() {
		StringBuilder queryBuilder = new StringBuilder(
				"SELECT * "
				+ "FROM validations.validation_rules "
				+ "WHERE isActive = TRUE "
				+ "ORDER BY priority ASC;\r\n"
				+ "");
		
		LOGGER.info("Generated GetValidatorQuery:{}", queryBuilder);
		return queryBuilder.toString();
	}

	@Override
	public Map<String, String> getValidatorParams(String validatorName) {
		LOGGER.info(" :: getActiveValidators ordered by priority");

		Map<String, String> validatorParams = null;
		SqlParameterSource src = new MapSqlParameterSource()
		        .addValue("validatorName", validatorName);
		try {
			List<Map<String, Object>> rows = namedJdbcTemplate.queryForList(
					generateGetValidatorParamsQuery(), src);
			
			// Extract validatorName values from the result set
			validatorParams = new HashMap<>();
		    for (Map<String, Object> row : rows) {
		        validatorParams.put(
		        		(String) row.get("paramName"), 
		        		(String) row.get("paramValue"));
		    }
			LOGGER.info(" :: load validatorParams from DB validatorName:{}|validatorParams:{}", validatorName, validatorParams);
		} catch (Exception e) {
			LOGGER.error("unable to get MerchantPaymentRequest Details " + e);
		}
		return validatorParams;
	}
	
	private String generateGetValidatorParamsQuery() {
		StringBuilder queryBuilder = new StringBuilder(
				"select validatorName, paramName, paramValue "
				+ "from validations.validation_rules_params "
				+ "where validatorName = :validatorName");
		
		LOGGER.info("generateGetValidatorParamsQuery:{}", queryBuilder);
		return queryBuilder.toString();
	}


}
