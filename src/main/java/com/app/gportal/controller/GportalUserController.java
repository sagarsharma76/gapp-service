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

import com.app.gportal.dto.GPortalUsersDTO;
import com.app.gportal.exceptions.DeleteNotAllowedException;
import com.app.gportal.exceptions.DuplicateDataException;
import com.app.gportal.exceptions.MandatoryFieldMissingException;
import com.app.gportal.exceptions.NotFoundException;
import com.app.gportal.response.ErrorResponse;
import com.app.gportal.response.GPortalResponse;
import com.app.gportal.response.GPortalUsersResponseDTO;
import com.app.gportal.service.IGportalUserService;

@RestController
@RequestMapping("/admin")
public class GportalUserController {

	private static final Logger log = LoggerFactory.getLogger(GportalUserController.class);

	@Autowired
	IGportalUserService gportalUserService;

	@CrossOrigin
	@PostMapping("/user")
	public ResponseEntity<GPortalResponse<GPortalUsersResponseDTO>> createUser(
			@RequestBody GPortalUsersDTO gPortalUsersDTO) {
		log.info("In method createUser for username=" + gPortalUsersDTO.getUserName());
		try {
			GPortalUsersResponseDTO gPortalUsersResponseDTO = this.gportalUserService
					.createGPortalUser(gPortalUsersDTO);
			return ResponseEntity.ok(new GPortalResponse<GPortalUsersResponseDTO>(true, null, gPortalUsersResponseDTO));
		} catch (MandatoryFieldMissingException | DuplicateDataException e) {
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString())
					.errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new GPortalResponse<GPortalUsersResponseDTO>(false, Arrays.asList(errorResponse), null));
		} catch (Exception e) {
			log.error("Exception occured in method createUser for name=" + gPortalUsersDTO.getUserName(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new GPortalResponse<GPortalUsersResponseDTO>(false, null, null));
		}
	}

	@CrossOrigin
	@PutMapping("/users/{id}")
	public ResponseEntity<GPortalResponse<GPortalUsersResponseDTO>> modifyUser(@PathVariable Long id,
			@RequestBody GPortalUsersDTO gPortalUsersDTO) {
		log.info("In method modifyUser for id=" + gPortalUsersDTO.getId());
		try {
			gPortalUsersDTO.setId(id);
			GPortalUsersResponseDTO gPortalUsersResponseDTO = this.gportalUserService
					.modifyGPortalUser(gPortalUsersDTO);
			return ResponseEntity.ok(new GPortalResponse<GPortalUsersResponseDTO>(true, null, gPortalUsersResponseDTO));
		} catch (NotFoundException e) {
			log.error("Not Found Exception occured in method modifyUser for id=" + gPortalUsersDTO.getId(), e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString())
					.errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new GPortalResponse<GPortalUsersResponseDTO>(false, Arrays.asList(errorResponse), null));
		} catch (MandatoryFieldMissingException | DuplicateDataException e) {
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString())
					.errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new GPortalResponse<GPortalUsersResponseDTO>(false, Arrays.asList(errorResponse), null));
		} catch (Exception e) {
			log.error("Exception occured in method modifyUser for id=" + gPortalUsersDTO.getId(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new GPortalResponse<GPortalUsersResponseDTO>(false, null, null));
		}
	}

	@CrossOrigin
	@GetMapping("/users")
	public ResponseEntity<GPortalResponse<List<GPortalUsersResponseDTO>>> getAllUsers() {
		log.info("In method getAllUsers to fetch all company master");
		try {
			return ResponseEntity.ok(new GPortalResponse<List<GPortalUsersResponseDTO>>(true, null,
					this.gportalUserService.getAllUsers()));
		} catch (Exception e) {
			log.error("Exception occured in method getAllUsers", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new GPortalResponse<List<GPortalUsersResponseDTO>>(false, null, null));
		}
	}

	@CrossOrigin
	@DeleteMapping("/users/{id}")
	public ResponseEntity<GPortalResponse<GPortalUsersResponseDTO>> deleteUser(@PathVariable Long id) {
		log.info("In method deleteUser to delete user for id=" + id);
		try {
			this.gportalUserService.deleteUser(id);
			return ResponseEntity.ok(new GPortalResponse<GPortalUsersResponseDTO>(true, null, null));
		} catch (NotFoundException e) {
			log.error("Not Found Exception occured in method deleteUser for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString())
					.errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new GPortalResponse<GPortalUsersResponseDTO>(false, Arrays.asList(errorResponse), null));
		} catch (DeleteNotAllowedException e) {
			log.error("DeleteNotAllowedException occured in method deleteHolderGroupMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString())
					.errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new GPortalResponse<GPortalUsersResponseDTO>(false, Arrays.asList(errorResponse), null));
		} catch (Exception e) {
			log.error("Exception occured in method deleteUser for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new GPortalResponse<GPortalUsersResponseDTO>(false, null, null));
		}
	}

	@CrossOrigin
	@GetMapping("/users/{id}")
	public ResponseEntity<GPortalResponse<GPortalUsersResponseDTO>> getUser(@PathVariable Long id) {
		log.info("In method getUser to get user for id=" + id);
		try {
			return ResponseEntity
					.ok(new GPortalResponse<GPortalUsersResponseDTO>(true, null, this.gportalUserService.getUser(id)));
		} catch (NotFoundException e) {
			log.error("Not Found Exception occured in method getUser for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString())
					.errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new GPortalResponse<GPortalUsersResponseDTO>(false, Arrays.asList(errorResponse), null));
		} catch (Exception e) {
			log.error("Exception occured in method getUser for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new GPortalResponse<GPortalUsersResponseDTO>(false, null, null));
		}
	}

}
