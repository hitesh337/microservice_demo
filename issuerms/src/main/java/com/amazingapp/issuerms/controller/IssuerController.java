package com.amazingapp.issuerms.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bouncycastle.crypto.agreement.srp.SRP6Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazingapp.issuerms.entity.IssuerEntity;
import com.amazingapp.issuerms.model.Issuer;
import com.amazingapp.issuerms.service.IssuerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@EnableHystrix
public class IssuerController {

	@Autowired
	private IssuerService issuerService;

	@GetMapping(path = "/issuer/custs")
	public List<IssuerEntity> getAllCustomers() {
		return issuerService.getAllCustomers();
	}

	@GetMapping(path = "/issuer/custs/{id}")
	public ResponseEntity getCustomerById(@PathVariable Long id) {
		return issuerService.getCustomerById(id);
	}
	
	@PostMapping(path = "/issuer/custs")
	public ResponseEntity issueBook(@RequestBody Issuer issuer) {
		return issuerService.issueBook(issuer);
	}
	
	@PostMapping(path = "/issuer/cancel/custs")
	public ResponseEntity cancelIssueBook(@RequestBody Issuer issuer) {
		System.out.println(issuer.toString());
		return issuerService.cancelIssueBook(issuer);
	}
	
	
}
