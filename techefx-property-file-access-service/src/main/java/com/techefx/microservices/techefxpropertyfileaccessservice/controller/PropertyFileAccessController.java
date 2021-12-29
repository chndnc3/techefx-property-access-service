package com.techefx.microservices.techefxpropertyfileaccessservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.techefx.microservices.techefxpropertyfileaccessservice.beans.PropertyAccessBean;
import com.techefx.microservices.techefxpropertyfileaccessservice.beans.PropertyAccessValue;

@RestController
@RequestMapping("/access")
public class PropertyFileAccessController {

	@Autowired
	PropertyAccessBean propertyAccessBean;

	@GetMapping("/accessPropertyFile")
	public PropertyAccessValue getPropertyFile() {
		refreshActuator();
		return new PropertyAccessValue(propertyAccessBean.getName(), propertyAccessBean.getDescription());
	}
	
	public void refreshActuator() {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8100/actuator/refresh";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		
		ResponseEntity<String> results = restTemplate.postForEntity(baseUrl, entity, String.class);
	}

}
