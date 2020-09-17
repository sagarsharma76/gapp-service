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

import com.app.gportal.dto.AccountNameMasterDTO;
import com.app.gportal.dto.TransactionRequestDTO;
import com.app.gportal.exceptions.DuplicateDataException;
import com.app.gportal.exceptions.MandatoryFieldMissingException;
import com.app.gportal.exceptions.NotFoundException;
import com.app.gportal.response.AccountNameMasterResponseDTO;
import com.app.gportal.response.ErrorResponse;
import com.app.gportal.response.GPortalResponse;
import com.app.gportal.response.MasterResponseTransactions;
import com.app.gportal.service.IAccountNameMasterservice;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("/anm")
public class AccountNameMasterController {

private static final Logger log = LoggerFactory.getLogger(AccountNameMasterController.class);
	
	@Autowired
	IAccountNameMasterservice accountNameMasterservice;
	
	@CrossOrigin
	@PostMapping("/user")
	public ResponseEntity<GPortalResponse<AccountNameMasterResponseDTO>> createAccountNameMaster(@RequestBody AccountNameMasterDTO accountNameMasterDTO) {
		log.info("In method createAccountNameMaster for name=" + accountNameMasterDTO.getName());
		try {
			AccountNameMasterResponseDTO accountHolderMasterResponseDTO = this.accountNameMasterservice.createAccountNameMaster(accountNameMasterDTO);
			return ResponseEntity.ok(new GPortalResponse<AccountNameMasterResponseDTO>(true, null, accountHolderMasterResponseDTO));
		}
		catch(MandatoryFieldMissingException | DuplicateDataException e) {
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<AccountNameMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method createAccountNameMaster for name=" + accountNameMasterDTO.getName(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<AccountNameMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	public ResponseEntity<GPortalResponse<AccountNameMasterResponseDTO>> modifyAccountNameMaster(@PathVariable Long id, @RequestBody AccountNameMasterDTO accountNameMasterDTO) {
		log.info("In method modifyAccountNameMaster for id=" + accountNameMasterDTO.getId());
		try {
			accountNameMasterDTO.setId(id);
			AccountNameMasterResponseDTO accountHolderMasterResponseDTO = this.accountNameMasterservice.modifyAccountNameMaster(accountNameMasterDTO);
			return ResponseEntity.ok(new GPortalResponse<AccountNameMasterResponseDTO>(true, null, accountHolderMasterResponseDTO));
		}
		catch(NotFoundException | MandatoryFieldMissingException | DuplicateDataException e) {
			log.error("Not Found Exception occured in method modifyAccountNameMaster for id=" + accountNameMasterDTO.getId(), e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<AccountNameMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method modifyAccountNameMaster for id=" + accountNameMasterDTO.getId(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<AccountNameMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@GetMapping("/all")
	public ResponseEntity<GPortalResponse<List<AccountNameMasterResponseDTO>>> getAllAccountNameMaster() {
		log.info("In method getAllAccountNameMaster to fetch all company master");
		try {
			return ResponseEntity.ok(new GPortalResponse<List<AccountNameMasterResponseDTO>>(true, null, this.accountNameMasterservice.getAllAccountNamerMaster()));
		}
		catch(Exception e) {
			log.error("Exception occured in method getAllAccountNameMaster", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<List<AccountNameMasterResponseDTO>>(false, null, null));
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<GPortalResponse<AccountNameMasterResponseDTO>> deleteAccountNameMaster(@PathVariable Long id) {
		log.info("In method deleteAccountNameMaster to delete company master for id=" + id);
		try {
			this.accountNameMasterservice.deleteAccountNameMaster(id);
			return ResponseEntity.ok(new GPortalResponse<AccountNameMasterResponseDTO>(true, null, null));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method deleteAccountNameMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<AccountNameMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method deleteAccountNameMaster for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<AccountNameMasterResponseDTO>(false, null, null));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GPortalResponse<AccountNameMasterResponseDTO>> getAccountNameMaster(@PathVariable Long id) {
		log.info("In method getAccountNameMaster for id=" + id);
		try {
			return ResponseEntity.ok(new GPortalResponse<AccountNameMasterResponseDTO>(true, null, this.accountNameMasterservice.getAccountNameMaster(id)));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method getAccountNameMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<AccountNameMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method getAccountNameMaster for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<AccountNameMasterResponseDTO>(false, null, null));
		}
	}
	
	
	@PutMapping("/transactions/{id}")
	public ResponseEntity<GPortalResponse<MasterResponseTransactions>> updateTransaction(@PathVariable Long id, 
			@RequestBody TransactionRequestDTO txnRequestDTO) {
		log.info("In method updateTransaction for id=" + id);
		try {
			return ResponseEntity.ok(new GPortalResponse<MasterResponseTransactions>(true, null, this.accountNameMasterservice.updateTransaction(id, txnRequestDTO)));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method getAccountNameMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<MasterResponseTransactions>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method getAccountNameMaster for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<MasterResponseTransactions>(false, null, null));
		}
	}
}
