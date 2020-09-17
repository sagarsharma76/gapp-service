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

import com.app.gportal.dto.CompanyMasterDTO;
import com.app.gportal.exceptions.DeleteNotAllowedException;
import com.app.gportal.exceptions.DuplicateDataException;
import com.app.gportal.exceptions.MandatoryFieldMissingException;
import com.app.gportal.exceptions.NotFoundException;
import com.app.gportal.response.CompanyMasterResponseDTO;
import com.app.gportal.response.ErrorResponse;
import com.app.gportal.response.GPortalResponse;
import com.app.gportal.response.TransactionResponseDTO;
import com.app.gportal.service.ICompanyMasterService;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class CompanyMasterController {

	private static final Logger log = LoggerFactory.getLogger(CompanyMasterController.class);
	
	@Autowired
	ICompanyMasterService companyMasterService;
	
	@CrossOrigin
	@PostMapping("/master")
	public ResponseEntity<GPortalResponse<CompanyMasterResponseDTO>> createCompanyMaster(@RequestBody CompanyMasterDTO companyMasterDTO) {
		log.info("In method createCompanyMaster for name=" + companyMasterDTO.getName());
		try {
			CompanyMasterResponseDTO companyMasterResponseDTO = this.companyMasterService.createCompanypMaster(companyMasterDTO);
			return ResponseEntity.ok(new GPortalResponse<CompanyMasterResponseDTO>(true, null, companyMasterResponseDTO));
		}
		catch(MandatoryFieldMissingException | DuplicateDataException e) {
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<CompanyMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method createCompanyMaster for name=" + companyMasterDTO.getName(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<CompanyMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@PutMapping("/master/{id}")
	public ResponseEntity<GPortalResponse<CompanyMasterResponseDTO>> modifyCompanyMaster(@PathVariable Long id, @RequestBody CompanyMasterDTO companyMasterDTO) {
		log.info("In method modifyCompanyMaster for id=" + companyMasterDTO.getId());
		try {
			companyMasterDTO.setId(id);
			CompanyMasterResponseDTO companyMasterResponseDTO = this.companyMasterService.modifyCompanypMaster(companyMasterDTO);
			return ResponseEntity.ok(new GPortalResponse<CompanyMasterResponseDTO>(true, null, companyMasterResponseDTO));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method modifyCompanyMaster for id=" + companyMasterDTO.getId(), e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<CompanyMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(MandatoryFieldMissingException | DuplicateDataException e) {
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<CompanyMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method modifyCompanyMaster for id=" + companyMasterDTO.getId(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<CompanyMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@GetMapping("/master")
	public ResponseEntity<GPortalResponse<List<CompanyMasterResponseDTO>>> getAllCompanyMaster() {
		log.info("In method getAllCompanyMaster to fetch all company master");
		try {
			return ResponseEntity.ok(new GPortalResponse<List<CompanyMasterResponseDTO>>(true, null, this.companyMasterService.getAllCompanyMaster()));
		}
		catch(Exception e) {
			log.error("Exception occured in method getAllCompanyMaster", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<List<CompanyMasterResponseDTO>>(false, null, null));
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/master/{id}")
	public ResponseEntity<GPortalResponse<CompanyMasterResponseDTO>> deleteCompanyMaster(@PathVariable Long id) {
		log.info("In method deleteCompanyMaster to delete company master for id=" + id);
		try {
			this.companyMasterService.deleteCompanyMaster(id);
			return ResponseEntity.ok(new GPortalResponse<CompanyMasterResponseDTO>(true, null, null));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method deleteCompanyMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<CompanyMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(DeleteNotAllowedException e) {
			log.error("DeleteNotAllowedException occured in method deleteHolderGroupMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<CompanyMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method deleteCompanyMaster for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<CompanyMasterResponseDTO>(false, null, null));
		}
	}
	
	@CrossOrigin
	@GetMapping("/master/{id}")
	public ResponseEntity<GPortalResponse<CompanyMasterResponseDTO>> getCompanyMaster(@PathVariable Long id) {
		log.info("In method getCompanyMaster to get company master for id=" + id);
		try {
			return ResponseEntity.ok(new GPortalResponse<CompanyMasterResponseDTO>(true, null, this.companyMasterService.getCompanyMaster(id)));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method getCompanyMaster for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<CompanyMasterResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method getCompanyMaster for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<CompanyMasterResponseDTO>(false, null, null));
		}
	}
	
	
	
	@CrossOrigin
	@GetMapping("/master/transactions")
	public ResponseEntity<GPortalResponse<List<TransactionResponseDTO>>> getAllCompanyMasterTransactions() {
		log.info("In method getAllCompanyMasterTransactions to fetch all company master");
		try {
			return ResponseEntity.ok(new GPortalResponse<List<TransactionResponseDTO>>(true, null, this.companyMasterService.getAllCompanyMasterNameAndTransactions()));
		}
		catch(Exception e) {
			log.error("Exception occured in method getAllCompanyMasterTransactions", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<List<TransactionResponseDTO>>(false, null, null));
		}
	}
	
	@CrossOrigin
	@GetMapping("/master/transactions/{id}")
	public ResponseEntity<GPortalResponse<TransactionResponseDTO>> getCompanyMasterTransactionsById(@PathVariable Long id) {
		log.info("In method getCompanyMasterTransactionsById to get company master for id=" + id);
		try {
			return ResponseEntity.ok(new GPortalResponse<TransactionResponseDTO>(true, null, this.companyMasterService.getCompanyTransactions(id)));
		}
		catch(NotFoundException e) {
			log.error("Not Found Exception occured in method getCompanyMasterTransactionsById for id=" + id, e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(e.getErrorCode().toString()).errorMessage(e.getErrorCode().getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<TransactionResponseDTO>(false, Arrays.asList(errorResponse), null));
		}
		catch(Exception e) {
			log.error("Exception occured in method getCompanyMasterTransactionsById for id=" + id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GPortalResponse<TransactionResponseDTO>(false, null, null));
		}
	}
	
	
	@CrossOrigin
	@PutMapping("/master/transactions/{id}/clear")
	public ResponseEntity<GPortalResponse<TransactionResponseDTO>> clearTransactions(@PathVariable Long id) {
		log.info("In method clearTransactions to get company master for id=" + id);
		try {
			this.companyMasterService.clearTransactions(id);
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