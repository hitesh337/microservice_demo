package com.amazingapp.oAuthServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager; 
//	
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("amazingbook")
                .secret("007")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(9000)
                .refreshTokenValiditySeconds(90000)
                .scopes("read");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore()).accessTokenConverter(tokenEnhancer());
    }
    
    @Bean
    public JwtAccessTokenConverter tokenEnhancer()
    {
    	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    	converter.setSigningKey("123456789012345678901234567890AB");
    	return converter;
    }
    
    @Bean 
    public JwtTokenStore tokenStore()
    {
    	return new JwtTokenStore(tokenEnhancer());
    }
}
