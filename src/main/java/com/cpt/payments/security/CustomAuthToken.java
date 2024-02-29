package com.cpt.payments.security;

import java.util.Arrays;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class CustomAuthToken extends AbstractAuthenticationToken {
	public CustomAuthToken() {
		super(Arrays.asList());
		super.setAuthenticated(true);
	}
	@Override
	public Object getCredentials() {
		return null;
	}
	@Override
	public Object getPrincipal() {
		return null;
	}
}

