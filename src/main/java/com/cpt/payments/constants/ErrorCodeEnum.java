package com.cpt.payments.constants;

import lombok.Getter;

public enum ErrorCodeEnum {
	
	GENERIC_EXCEPTION("10001","Something went wrong, please try later"),
	PROVIDER_ID_VALIDATION_FAILED("10017","Bad request, given proiderId parameter is not valid or empty");
	
	@Getter
	private String errorCode;
	@Getter
	private String errorMessage;
	
	private ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode=errorCode;
		this.errorMessage=errorMessage;
	}

}