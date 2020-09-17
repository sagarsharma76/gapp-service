package com.app.gportal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gportal.model.Status;
import com.app.gportal.repository.IStatusRepository;
import com.app.gportal.response.StatusResponseDTO;
import com.app.gportal.service.IStatusService;

@Service
public class StatusService implements IStatusService {

	private static final Logger log = LoggerFactory.getLogger(StatusService.class);
	
	@Autowired
	private IStatusRepository statusRepository;
	
	@Override
	public List<StatusResponseDTO> getAllStatus() {
		log.info("In method getAllStatus");
		List<Status> statuses = this.statusRepository.findAll();
		List<StatusResponseDTO> statusResponses = new ArrayList<>();
		 for(Status status : statuses) {
			 statusResponses.add(this.parseDataFromDB(status));
		 }
		 return statusResponses;
	}
	
	
	private StatusResponseDTO parseDataFromDB(Status status) {
		return StatusResponseDTO.builder().id(status.getId()).name(status.getName()).build();
	}

}
