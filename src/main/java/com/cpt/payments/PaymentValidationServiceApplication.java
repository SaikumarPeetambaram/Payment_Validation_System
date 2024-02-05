package com.cpt.payments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cpt.payments.controller.PaymentsController;

@SpringBootApplication(scanBasePackages = { "com.cpt.payments" },exclude = {UserDetailsServiceAutoConfiguration.class })
@EnableAsync
@EnableScheduling
public class PaymentValidationServiceApplication {
	
	private static final Logger LOGGER = LogManager.getLogger(PaymentValidationServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PaymentValidationServiceApplication.class, args);
		LOGGER.info("application started");
	}

}
