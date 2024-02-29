package com.cpt.payments.security;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cpt.payments.service.HmacSha256Provider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class HmacFilter extends OncePerRequestFilter {
	private static final Logger LOGGER = LogManager.getLogger(HmacFilter.class);

	private HmacSha256Provider hmacSha256Provider;

	public HmacFilter(HmacSha256Provider hmacSha256Provider) {
		this.hmacSha256Provider = hmacSha256Provider;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		LOGGER.info(">> in HmacFilter ");

		String requestSignature = servletRequest.getHeader("signature");
		
		WrappedRequest wrappedRequest = new WrappedRequest(servletRequest);
		String requestDataAsJson = wrappedRequest.getBody().replaceAll("\\s", "");

		/*//reads request body from HttpServletRequest StringBuilder request
		 * StringBuilder requestBody = new StringBuilder(); BufferedReader reader =
		 * servletRequest.getReader(); String line; while ((line = reader.readLine()) !=
		 * null) { requestBody.append(line); } reader.close();
		 */
		
		//String requestDataAsJson = requestBody.toString().replaceAll("\\s", "");

		boolean isVerified = hmacSha256Provider.isSigValid(
				requestDataAsJson, requestSignature);

 		if (isVerified) {
			LOGGER.info(">> in HmacFilter >> signature verified and proceeding further");

			Authentication auth = new CustomAuthToken();
			SecurityContextHolder.getContext().setAuthentication(auth);

			filterChain.doFilter(wrappedRequest, servletResponse);
			LOGGER.info(">> in HmacFilter after calls");

		}
	}
}

