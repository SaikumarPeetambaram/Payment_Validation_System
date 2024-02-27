package com.cpt.payments.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.controller.PaymentsController;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.service.HmacSha256Provider;
import com.cpt.payments.utils.HmacSha256Utils;

@Component
public class HmacSha256ProviderImpl implements HmacSha256Provider {
	
	private static final Logger LOGGER = LogManager.getLogger(HmacSha256ProviderImpl.class);
	
	@Autowired
	private HmacSha256Utils hmacSha256Utils;
	
	@Value("${payment.signatureKey}")
	private String secretKey;
	
	@Override
	public boolean isSigValid(String requestDataAsJson, String requestSignature) {

		if(requestSignature == null || requestSignature.trim().isEmpty()) {
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.SIGNATURE_EMPTY.getErrorCode(),
					ErrorCodeEnum.SIGNATURE_EMPTY.getErrorMessage());
		}

		boolean isSignatureValid = hmacSha256Utils.verifyHmac(secretKey, requestDataAsJson, requestSignature);

		if(!isSignatureValid){
			//What to do?
			//Throw Exception with some errorcode and errormessage
			LOGGER.warn("SIGNATURE NOT VALID");
			throw new ValidationException(HttpStatus.BAD_REQUEST,
					ErrorCodeEnum.SIGNATURE_INVALID.getErrorCode(),
					ErrorCodeEnum.SIGNATURE_INVALID.getErrorMessage());
		}
		
		//Signature is valid
		return true;
	}

}
