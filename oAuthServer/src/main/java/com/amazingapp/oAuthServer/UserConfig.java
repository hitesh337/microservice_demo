package com.amazingapp.oAuthServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService users() 
	{
		UserDetails user = User.builder()
				.username("john")
				.password("1234")
				.roles("USER")
				.authorities("ISSUER_FULL")
				.build();
		UserDetails admin = User.builder()
				.username("thomas")
				.password("1234")
				.roles("USER", "ADMIN")
				.authorities("ISSUER_FULL","BOOKS_FULL").build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	/*@Bean
	public UserDetailsService userDetailsService() {
		UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		UserDetails user = User.withUsername("john").password("12345").authorities("read").build();
		userDetailsManager.createUser(user);
		return userDetailsManager;
	}*/

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}