package com.cpt.payments.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.utils.HmacSha256Utils;

@ExtendWith(MockitoExtension.class)
public class HmacSha256ProviderImplTest {
	
	@Mock
	private HmacSha256Utils hmacSha256Utils;
	
	@InjectMocks
	HmacSha256ProviderImpl hmacSha256ProviderImpl;
	
	@Test
	public void testNullSignature() {
		//data defining
		String requestDataAsJson = null;
		String requestSignature = null;
		
		//Invocation
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> hmacSha256ProviderImpl.isSigValid(
						requestDataAsJson, requestSignature));
		
		//verification
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.SIGNATURE_EMPTY.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.SIGNATURE_EMPTY.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	public void testBlankSignature() {
		//data defining
		String requestDataAsJson = null;
		String requestSignature = "      ";
		
		//Invocation
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> hmacSha256ProviderImpl.isSigValid(
						requestDataAsJson, requestSignature));
		
		//verification
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.SIGNATURE_EMPTY.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.SIGNATURE_EMPTY.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	public void testEmptySignature() {
		//data defining
		String requestDataAsJson = null;
		String requestSignature = "";
		
		//Invocation
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> hmacSha256ProviderImpl.isSigValid(
						requestDataAsJson, requestSignature));
		
		//verification
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.SIGNATURE_EMPTY.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.SIGNATURE_EMPTY.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	public void testInvalidSignature() {
		//data defining
		String requestDataAsJson = null;
		String requestSignature = "input-test-signature";
		
		//Invocation
		ValidationException returnedException = assertThrows(ValidationException.class, 
				() -> hmacSha256ProviderImpl.isSigValid(
						requestDataAsJson, requestSignature));
		
		//verification
		assertEquals(
				HttpStatus.BAD_REQUEST, 
				returnedException.getHttpStatus());
		
		assertEquals(
				ErrorCodeEnum.SIGNATURE_INVALID.getErrorCode(), 
				returnedException.getErrorCode());
		
		assertEquals(
				ErrorCodeEnum.SIGNATURE_INVALID.getErrorMessage(), 
				returnedException.getErrorMessage());
	}
	
	@Test
	public void testValidSignature() {
		//data defining
		String requestDataAsJson = null;
		String requestSignature = "input-test-signature";
		
		when(hmacSha256Utils.verifyHmac(any(), any(), any())).thenReturn(true);
		
		//Invocation
		boolean isSigValid = assertDoesNotThrow(
				() -> hmacSha256ProviderImpl.isSigValid(
						requestDataAsJson, requestSignature));
		
		//verification
		assertTrue(isSigValid);
	}
}
