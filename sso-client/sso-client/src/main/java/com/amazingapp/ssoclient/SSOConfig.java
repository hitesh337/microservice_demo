package com.amazingapp.ssoclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class SSOConfig {

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository()
	{
		return new InMemoryClientRegistrationRepository(clientRegistration());
	}
	
	private ClientRegistration clientRegistration() {
		return CommonOAuth2Provider.GITHUB
				.getBuilder("github")
				.clientId("5921b0f75025e13b560b")
				.clientSecret("1a6ef9189eff89879c71cc89d61db5013c58f1dd")
				.build();
	}
}
