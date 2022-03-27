package com.amazingapp.apigateway;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig  {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
        		.csrf()
        		.disable()
                .authorizeExchange()
//                .pathMatchers("/issuerms/issuer/custs/{Id}").hasRole("ISSUER_FULL")
//                .pathMatchers("/issuerms/issuer/custs").hasAuthority("ISSUER_FULL")
//                .pathMatchers("/issuerms/issuer/cancel/custs").hasAuthority("ISSUER_FULL")
//                .pathMatchers("/bookms/**").hasAnyAuthority("ISSUER_FULL","BOOKS_FULL")
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();

        return httpSecurity.build();
    }
    


	@Bean
    public ReactiveJwtDecoder jwtDecoder() {
        byte[] keyBytes = "123456789012345678901234567890AB".getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
        return NimbusReactiveJwtDecoder.withSecretKey(secretKeySpec).build();
    }
}

