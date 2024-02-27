package com.cpt.payments.service;

public interface HmacSha256Provider {
	
	public boolean isSigValid(String requestDataAsJson, String requestSignature);

}
