package com.app.gportal.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.gportal.response.GPortalResponse;
import com.app.gportal.response.StatusResponseDTO;
import com.app.gportal.service.IStatusService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("/status")
public class StatusController {

	private static final Logger log = LoggerFactory.getLogger(StatusController.class);
	
	@Autowired
	private IStatusService statusService;
	
	@CrossOrigin
	@GetMapping("/list")
	public ResponseEntity<GPortalResponse<List<StatusResponseDTO>>> getAllStatus() {
		log.info("In method getAllStatus");
		try {
			return ResponseEntity.ok(new GPortalResponse<List<StatusResponseDTO>>(true, null, this.statusService.getAllStatus()));
		}
		catch(Exception e) {
			log.error("Exception occured in method getAllStatus", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<List<StatusResponseDTO>>(false, null, null));
		}
	}
}
