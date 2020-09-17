package com.app.gportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
public class HealthController {

	private static final Logger log = LoggerFactory.getLogger(HealthController.class);

	@GetMapping("/health")
	public String checkHealth() {
		log.info("In method checkHealth. Service is up and running");
		return "Service is up and running";
	}
	
	
	@GetMapping("/health1")
	public String checkHealth1() {
		log.info("In method checkHealth. Service is up and running");
		return "Service is up and running";
	}

}
