package com.cpt.payments.service.impl.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.MerchantPaymentSaveResult;
import com.cpt.payments.dao.MerchantPaymentRequestDao;
import com.cpt.payments.dto.MerchantPaymentRequest;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.Validator;
import com.google.gson.Gson;

@Component
public class DuplicationTransactionValidator implements Validator {
	
	private static final Logger LOGGER = LogManager.getLogger(DuplicationTransactionValidator.class);
	
	@Autowired
	private MerchantPaymentRequestDao merchantPaymentRequestDao;
	
	/**
	 * 1. make a DB select to check, if value exist.

			2. if exists, return error code & error message

			3. If value does not exist, then insert record 
				DB will allows only 1 requeset to be inserted (unique). 
				Rest all 4 or 400, all will be rejected.

			4. if successfully inserted, then continue with process 1 request will get success.

			5. if failed, then return error code & error message rest 4 members will get failure
	 *
	 *if problem then throw exception
	 *if no issue then don't do anything
	 */

	@Override
	public void doValidate(PaymentRequest paymentRequest) {
		String merchantTransactionId = paymentRequest.getPayment().getMerchantTransactionReference();
		String email = paymentRequest.getUser().getEmail();
		
		MerchantPaymentRequest merchantPaymentRequest = merchantPaymentRequestDao.getMerchantPaymentRequest(merchantTransactionId);
		LOGGER.info( " merchantPaymentRequest is  -> "+merchantPaymentRequest);
		if(null != merchantPaymentRequest) {
			LOGGER.info( " Duplicate Transaction ");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.DUPLICATE_TRANSACTION.getErrorCode(),
					ErrorCodeEnum.DUPLICATE_TRANSACTION.getErrorMessage());
		} else {
			Gson gson = new Gson();
			merchantPaymentRequest = MerchantPaymentRequest.builder()
					.merchantTransactionReference(merchantTransactionId)
					.email(email)
					.transactionRequest(gson.toJson(paymentRequest))
					.build();
			LOGGER.info( " prepared merchantPaymentRequest is  -> "+merchantPaymentRequest);
			
			MerchantPaymentSaveResult response = 
					merchantPaymentRequestDao.insertMerchantPaymentRequest(merchantPaymentRequest);
			
			if(response == MerchantPaymentSaveResult.IS_DUPLICATE) {
				LOGGER.warn( "Duplicate Transaction while insert");
				throw new ValidationException(HttpStatus.BAD_REQUEST,
						ErrorCodeEnum.DUPLICATE_TRANSACTION.getErrorCode(),
						ErrorCodeEnum.DUPLICATE_TRANSACTION.getErrorMessage());
			}
			
			if(response == MerchantPaymentSaveResult.IS_ERROR) {
				LOGGER.error( "Error saving MerchantPaymentRequest in DB");
				throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR,
						ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(),
						ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage());
			}
		}
	}
}
