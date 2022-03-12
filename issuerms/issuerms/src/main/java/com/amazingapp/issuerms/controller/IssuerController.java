package com.amazingapp.issuerms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazingapp.issuerms.model.Issuer;
import com.amazingapp.issuerms.repository.IssuerRepository;
import java.util.List;

@RestController
public class IssuerController {

	@Autowired
	private IssuerRepository issuerRepository;
	
	@GetMapping(path = "/issuer/cust")
	public List<Issuer> getAllCustomer()
	{
		return issuerRepository.findAll();
	}
	
	@GetMapping(path = "/issuer/cust/{id}")
	public ResponseEntity getCustomerById(@PathVariable Long id)
	{
		List<Issuer> cust = issuerRepository.findByCustId(id);
		if(cust.size() > 0)
			return ResponseEntity.ok(cust);
		return ResponseEntity.notFound().build();	
	}
	
	
}
