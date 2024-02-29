package com.cpt.payments.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cpt.payments.exceptions.ExceptionHandlerFilter;
import com.cpt.payments.security.HmacFilter;
import com.cpt.payments.service.HmacSha256Provider;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private HmacSha256Provider hmacSha256Provider;
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
	    .csrf(csrf -> csrf.disable())
	    .authorizeHttpRequests(
	    		authorize -> 
	    		authorize
	    		.anyRequest().authenticated())
	    .addFilterBefore(new ExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
	    .addFilterAfter(new HmacFilter(hmacSha256Provider), UsernamePasswordAuthenticationFilter.class)
	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
	}
}

