package com.app.gportal.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.gportal.dto.HolderGroupMasterDTO;
import com.app.gportal.exceptions.DeleteNotAllowedException;
import com.app.gportal.exceptions.DuplicateDataException;
import com.app.gportal.exceptions.MandatoryFieldMissingException;
import com.app.gportal.exceptions.NotFoundException;
import com.app.gportal.response.ErrorResponse;
import com.app.gportal.response.GPortalResponse;
import com.app.gportal.response.HolderGroupMasterResponseDTO;
import com.app.gportal.service.IHolderGroupMasterService;

@RestController
@RequestMapping("/hgm")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class HolderGroupMasterController {

	private static final Logger log = LoggerFactory.getLogger(HealthController.class);
	
	@Autowired
	IHolderGroupMasterService holderGroupMasterService;
	
	@CrossOrigin
	@PostMapping("/user")
	public ResponseEntity<GPortalResponse<HolderGroupMasterResponseDTO>> createHolderGroupMaster(@RequestBody HolderGroupMasterDTO holderGroupMasterDTO) {
		log.info("In method createHolderGroupMaster for name=" + holderGroupMasterDTO.getName());
		try {
			HolderGroupMasterResponseDTO obj = this.holderGroupMasterService.createHolderGroupMaster(holderGroupMasterDTO);
			return ResponseEntity.ok(new GPortalResponse<HolderGroupMasterResponseDTO>(true, null, obj));
		}
		catch(MandatoryFieldMissingException | DuplicateDataException e) {
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<HolderGroupMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method createHolderGroupMaster for name=" + holderGroupMasterDTO.getName(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<HolderGroupMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@PutMapping("/users/{id}")
	public ResponseEntity<GPortalResponse<HolderGroupMasterResponseDTO>> modifyHolderGroupMaster(@PathVariable Long id, @RequestBody HolderGroupMasterDTO holderGroupMasterDTO) {
		log.info("In method modifyCompanypMaster for id=" + holderGroupMasterDTO.getId());
		try {
			holderGroupMasterDTO.setId(id);
			HolderGroupMasterResponseDTO gportaUserResponseDTO = this.holderGroupMasterService.modifyHolderGroupMaster(holderGroupMasterDTO);
			return ResponseEntity.ok(new GPortalResponse<HolderGroupMasterResponseDTO>(true, null, gportaUserResponseDTO));
		}
		catch(MandatoryFieldMissingException | DuplicateDataException e) {
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<HolderGroupMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method modifyCompanypMaster for id=" + holderGroupMasterDTO.getId(), e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<HolderGroupMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method modifyCompanypMaster for id=" + holderGroupMasterDTO.getId(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<HolderGroupMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@GetMapping("/users")
	public ResponseEntity<GPortalResponse<List<HolderGroupMasterResponseDTO>>> getAllHolderGroupMasters() {
		log.info("In method getAllHolderGroupMasters to fetch all holder group masters");
		try {
			return ResponseEntity.ok(new GPortalResponse<List<HolderGroupMasterResponseDTO>>(true, null, this.holderGroupMasterService.getAllHolderGrouprMaster()));
		}
		catch(Exception e) {
			log.error("Exception occured in method getAllHolderGroupMasters", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<List<HolderGroupMasterResponseDTO>>(false, null, null));
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/users/{id}")
	public ResponseEntity<GPortalResponse<HolderGroupMasterResponseDTO>> deleteHolderGroupMaster(@PathVariable Long id) {
		log.info("In method deleteHolderGroupMaster to delete holder group master for id=" + id);
		try {
			this.holderGroupMasterService.deleteHolderGroupMaster(id);
			return ResponseEntity.ok(new GPortalResponse<HolderGroupMasterResponseDTO>(true, null, null));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method deleteHolderGroupMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<HolderGroupMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(DeleteNotAllowedException e) {
			log.error("DeleteNotAllowedException occured in method deleteHolderGroupMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<HolderGroupMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method deleteHolderGroupMaster for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<HolderGroupMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@GetMapping("/users/{id}")
	public ResponseEntity<GPortalResponse<HolderGroupMasterResponseDTO>> getHolderGroupMaster(@PathVariable Long id) {
		log.info("In method getHolderGroupMaster to get holder group master for id=" + id);
		try {
			return ResponseEntity.ok(new GPortalResponse<HolderGroupMasterResponseDTO>(true, null, this.holderGroupMasterService.getHolderGroupMaster(id)));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method getHolderGroupMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<HolderGroupMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method getHolderGroupMaster for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<HolderGroupMasterResponseDTO>(false, null, null));
		}
	}
}
