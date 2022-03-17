package com.amazingapp.ssoclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class SsoClientApplication extends WebSecurityConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(SsoClientApplication.class, args);
	}
	
	@GetMapping("/home")
	public String getHome() {
		return "home.html";
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.oauth2Login();
		http.authorizeRequests().anyRequest().authenticated();
	}
	
	
}
