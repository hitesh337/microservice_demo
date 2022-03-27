package com.amazingapp.issuerms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazingapp.issuerms.entity.IssuerEntity;
import com.amazingapp.issuerms.model.Issuer;
import com.amazingapp.issuerms.repository.IssuerRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class IssuerService {

	@Autowired
	private IssuerRepository issuerRepository;

	@Autowired
	private RestTemplate restTemplate;

	public List<IssuerEntity> getAllCustomers() {
		return issuerRepository.findAll();
	}

	public ResponseEntity getCustomerById(Long id) {
		List<IssuerEntity> cust = issuerRepository.findByCustId(id);
		if (cust.size() > 0) {
			return ResponseEntity.ok(cust);
		}
		return ResponseEntity.notFound().build();
	}

	@Transactional
	@HystrixCommand(fallbackMethod = "issuemsFallBack",
    commandProperties = {
   		 @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "8000"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
	public ResponseEntity issueBook(Issuer issuer) {
		IssuerEntity entity = convertModelToEntity(issuer,"issued");
		Map<String, String> hm = new HashMap<String, String>();
		long cnt = issuerRepository.countByCustIdAndBookIdAndStatus(entity.getCustId(), entity.getBookId(),"ISSUED");
		if (cnt == 0) {
			Map<String, Long> params = new HashMap<String, Long>();
			params.put("bookId", issuer.getBookId());
			System.out.println(" ========  before calling ");
			Map values = restTemplate.getForObject("http://bookms/book/{bookId}", Map.class, params);
			System.out.println(" ========  after calling ");
			int noOfbooks = values.get("totalCopies") != null ? Integer.parseInt(values.get("totalCopies").toString()): 0;
			int issuedbooks = values.get("issuedCopies") != null ? Integer.parseInt(values.get("issuedCopies").toString()): 0;
			if (noOfbooks-issuedbooks > 0) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("id", issuer.getBookId());
				obj.put("issuedCopies", (issuer.getNoOfCopies() + issuedbooks));
				HttpEntity<String> httpEntity = new HttpEntity<String>(obj.toString(), headers);
				issuerRepository.save(entity);
				restTemplate.put("http://bookms/book/{id}/{issuedCopies}", httpEntity, obj);
				hm.put("message", "User id " + issuer.getCustId() +" issued the book with Book id = "+issuer.getBookId());
				return ResponseEntity.ok(hm);
			} else {
				hm.put("message", "Book id " + issuer.getBookId()+" is not available.");
				return ResponseEntity.ok(hm);

			}
		} else {
			hm.put("message", "User id " + issuer.getCustId() +" have already issued the book with Book id = "+issuer.getBookId());
			return ResponseEntity.ok(hm);
		}
	}

	@Transactional
	@HystrixCommand(fallbackMethod = "issuemsFallBack",
    commandProperties = {
   		 @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "8000"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
	public ResponseEntity cancelIssueBook(Issuer issuer) {
		IssuerEntity entity = convertModelToEntity(issuer,"returned");
		Map<String, String> hm = new HashMap<String, String>();
		long cnt = issuerRepository.countByCustIdAndBookIdAndStatus(entity.getCustId(), entity.getBookId(),"ISSUED");
		long issueId = issuerRepository.findIssueIdByCustIdAndBookId(entity.getCustId(), entity.getBookId());
		entity.setIssueId(issueId);
		if (cnt != 0)
		{
			Map<String, Long> params = new HashMap<String, Long>();
			params.put("bookId", issuer.getBookId());
			Map values = restTemplate.getForObject("http://bookms/book/{bookId}", Map.class, params);
			int noOfbooks = values.get("totalCopies") != null ? Integer.parseInt(values.get("totalCopies").toString()): 0;
			int issuedbooks = values.get("issuedCopies") != null ? Integer.parseInt(values.get("issuedCopies").toString()): 0;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("id", issuer.getBookId());
			obj.put("issuedCopies", (issuedbooks -issuer.getNoOfCopies()));
			HttpEntity<String> httpEntity = new HttpEntity<String>(obj.toString(), headers);
			issuerRepository.save(entity);
			restTemplate.put("http://bookms/book/{id}/{issuedCopies}", httpEntity, obj);
			hm.put("message", "User id " + issuer.getCustId() +" return the book with Book id = "+issuer.getBookId());
			return ResponseEntity.ok(hm);
		}
		else 
		{
			hm.put("message", "User id " + issuer.getCustId() +" do not issued any book with Book id = "+issuer.getBookId());
			return ResponseEntity.ok(hm);
		}
	}

	
	private IssuerEntity convertModelToEntity(Issuer issuer,String status) {
		IssuerEntity entity = new IssuerEntity();
		entity.setBookId(issuer.getBookId());
		entity.setCustId(issuer.getCustId());
		entity.setIssueId(issuer.getIssueId());
		entity.setNoOfCopies(issuer.getNoOfCopies());
		entity.setStatus(status.toUpperCase());
		return entity;
	}
	
	private ResponseEntity issuemsFallBack(Issuer issuer) {
		Map hm =new HashMap();
		hm.put("message", "Can Not Fetch Book Details, Service is down");
		return ResponseEntity.ok(hm);
	}

}
