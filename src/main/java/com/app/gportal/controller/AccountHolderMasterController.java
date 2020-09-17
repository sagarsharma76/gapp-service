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

import com.app.gportal.dto.AccountHolderMasterDTO;
import com.app.gportal.exceptions.DeleteNotAllowedException;
import com.app.gportal.exceptions.DuplicateDataException;
import com.app.gportal.exceptions.MandatoryFieldMissingException;
import com.app.gportal.exceptions.NotFoundException;
import com.app.gportal.response.AccountHolderMasterResponseDTO;
import com.app.gportal.response.ErrorResponse;
import com.app.gportal.response.GPortalResponse;
import com.app.gportal.response.TransactionResponseDTO;
import com.app.gportal.service.IAccountHolderMasterService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("/ahm")
public class AccountHolderMasterController {

private static final Logger log = LoggerFactory.getLogger(AccountHolderMasterController.class);
	
	@Autowired
	IAccountHolderMasterService accountHolderMasterService;
	
	@CrossOrigin
	@PostMapping("/user")
	public ResponseEntity<GPortalResponse<AccountHolderMasterResponseDTO>> createAccountHolderMaster(@RequestBody AccountHolderMasterDTO accountHolderMasterDTO) {
		log.info("In method createAccountHolderMaster for name=" + accountHolderMasterDTO.getName());
		try {
			AccountHolderMasterResponseDTO accountHolderMasterResponseDTO = this.accountHolderMasterService.createAccountHolderMaster(accountHolderMasterDTO);
			return ResponseEntity.ok(new GPortalResponse<AccountHolderMasterResponseDTO>(true, null, accountHolderMasterResponseDTO));
		}
		catch(MandatoryFieldMissingException | DuplicateDataException e) {
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<AccountHolderMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method createAccountHolderMaster for name=" + accountHolderMasterDTO.getName(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<AccountHolderMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	public ResponseEntity<GPortalResponse<AccountHolderMasterResponseDTO>> modifyAccountHolderMaster(@PathVariable Long id, @RequestBody AccountHolderMasterDTO accountHolderMasterDTO) {
		log.info("In method modifyAccountHolderMaster for id=" + accountHolderMasterDTO.getId());
		try {
			accountHolderMasterDTO.setId(id);
			AccountHolderMasterResponseDTO accountHolderMasterResponseDTO = this.accountHolderMasterService.modifyAccountHolderMater(accountHolderMasterDTO);
			return ResponseEntity.ok(new GPortalResponse<AccountHolderMasterResponseDTO>(true, null, accountHolderMasterResponseDTO));
		}
		catch(NotFoundException | MandatoryFieldMissingException | DuplicateDataException e) {
			log.error("Exception occured in method modifyAccountHolderMaster for id=" + accountHolderMasterDTO.getId(), e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<AccountHolderMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method modifyAccountHolderMaster for id=" + accountHolderMasterDTO.getId(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<AccountHolderMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@GetMapping("/all")
	public ResponseEntity<GPortalResponse<List<AccountHolderMasterResponseDTO>>> getAllAccountHolderMaster() {
		log.info("In method getAllAccountHolderMaster to fetch all company master");
		try {
			return ResponseEntity.ok(new GPortalResponse<List<AccountHolderMasterResponseDTO>>(true, null, this.accountHolderMasterService.getAllAccountHolderMaster()));
		}
		catch(Exception e) {
			log.error("Exception occured in method getAllAccountHolderMaster", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<List<AccountHolderMasterResponseDTO>>(false, null, null));
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<GPortalResponse<AccountHolderMasterResponseDTO>> deleteAccountHolderGroupMaster(@PathVariable Long id) {
		log.info("In method deleteCompanyMaster to delete company master for id=" + id);
		try {
			this.accountHolderMasterService.deleteAccountHolderMaster(id);
			return ResponseEntity.ok(new GPortalResponse<AccountHolderMasterResponseDTO>(true, null, null));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method deleteAccountHolderGroupMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<AccountHolderMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(DeleteNotAllowedException e) {
			log.error("DeleteNotAllowedException occured in method deleteHolderGroupMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<AccountHolderMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method deleteAccountHolderGroupMaster for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<AccountHolderMasterResponseDTO>(false, null, null));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GPortalResponse<AccountHolderMasterResponseDTO>> getAccountHolderGroupMaster(@PathVariable Long id) {
		log.info("In method getAccountHolderGroupMaster to get company master for id=" + id);
		try {
			return ResponseEntity.ok(new GPortalResponse<AccountHolderMasterResponseDTO>(true, null, this.accountHolderMasterService.getAccountHolderMaster(id)));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method getAccountHolderGroupMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<AccountHolderMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method getAccountHolderGroupMaster for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<AccountHolderMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@GetMapping("/transactions/{id}")
	public ResponseEntity<GPortalResponse<TransactionResponseDTO>> getAccountHolderTransactions(@PathVariable Long id) {
		log.info("In method getAccountHolderTransactions to get transactions for id=" + id);
		try {
			return ResponseEntity.ok(new GPortalResponse<TransactionResponseDTO>(true, null, this.accountHolderMasterService.getAccountHolderNameTransactions(id)));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method getAccountHolderTransactions for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<TransactionResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method getAccountHolderTransactions for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<TransactionResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@PutMapping("/transactions/{id}/clear")
	public ResponseEntity<GPortalResponse<TransactionResponseDTO>> clearTransactions(@PathVariable Long id) {
		log.info("In method clearTransactions to get company master for id=" + id);
		try {
			this.accountHolderMasterService.clearTransactions(id);
			return ResponseEntity.ok(new GPortalResponse<TransactionResponseDTO>(true, null, null));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method clearTransactions for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<TransactionResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method clearTransactions for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<TransactionResponseDTO>(false, null, null));
		}
	}
}
