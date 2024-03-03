package com.cpt.payments.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cpt.payments.constants.MerchantPaymentSaveResult;
import com.cpt.payments.dao.MerchantPaymentRequestDao;
import com.cpt.payments.dto.MerchantPaymentRequest;

@Repository
public class MerchantPaymentRequestDaoImpl implements MerchantPaymentRequestDao {

	private static final Logger LOGGER = LogManager.getLogger(MerchantPaymentRequestDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public MerchantPaymentRequest getMerchantPaymentRequest(String merchantTransactionId) {
		LOGGER.info( " :: fetching MerchantPaymentRequest Details  for :: {}" , merchantTransactionId);

		MerchantPaymentRequest merchantPaymentRequest = null;
		try {
			merchantPaymentRequest = namedJdbcTemplate.queryForObject(getMerchantPaymentRequest(),
					new BeanPropertySqlParameterSource(MerchantPaymentRequest.builder()
							.merchantTransactionReference(merchantTransactionId).build()),
					new BeanPropertyRowMapper<>(MerchantPaymentRequest.class));
			LOGGER.info( " :: merchantPaymentRequest Details from DB  = " + merchantPaymentRequest);
		} catch (Exception e) {
			LOGGER.info( "unable to get MerchantPaymentRequest Details " + e);
			//LogMessage.logException(LOGGER, e);
		}
		return merchantPaymentRequest;
	}

	private String getMerchantPaymentRequest() {
		StringBuilder queryBuilder = new StringBuilder(
				"SELECT * FROM merchant_payment_request WHERE merchantTransactionReference = :merchantTransactionReference ");
		LOGGER.info( "getMerchantPaymentRequest  = " + queryBuilder);
		return queryBuilder.toString();
	}

	@Override
	public MerchantPaymentSaveResult insertMerchantPaymentRequest(MerchantPaymentRequest merchantPaymentRequest) {
		try {
			namedJdbcTemplate.update(insertMerchantPaymentRequest(),
					new BeanPropertySqlParameterSource(merchantPaymentRequest));
			
			return MerchantPaymentSaveResult.IS_SAVED;
		} catch (DuplicateKeyException e) {
			LOGGER.error("DuplicateKeyException while saving MerchantPaymentRequest session in DB :: " + merchantPaymentRequest);
			
			return MerchantPaymentSaveResult.IS_DUPLICATE;
		} catch (Exception e) {
			LOGGER.error("exception while saving MerchantPaymentRequest session in DB :: " + merchantPaymentRequest);
			
			return MerchantPaymentSaveResult.IS_ERROR;
		}

	}

	private String insertMerchantPaymentRequest() {
		StringBuilder queryBuilder = new StringBuilder("INSERT INTO merchant_payment_request ");
		queryBuilder.append("(merchantTransactionReference, email, transactionRequest) ");
		queryBuilder.append("VALUES(:merchantTransactionReference, :email, :transactionRequest)");
		LOGGER.info( "insertMerchantPaymentRequest  = " + queryBuilder);
		return queryBuilder.toString();
	}

}
