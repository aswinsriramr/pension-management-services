package com.pension.processpensioninput.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class FeignConfig {
	@Autowired
	AuthorizationContext authorizationContext;

	@Bean
	public RequestInterceptor requestInterceptor() {

		return requestTemplate -> {
			String authorizationToken = getAuthorizationContext().getAuthorization();
			requestTemplate.header("Content-Type", "application/json");
			requestTemplate.header("Accept", "application/json");
			requestTemplate.header("Authorization", authorizationToken);

		};
	}

	public AuthorizationContext getAuthorizationContext() {
		return authorizationContext;
	}
}
