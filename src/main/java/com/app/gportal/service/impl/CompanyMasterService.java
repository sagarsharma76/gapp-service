package com.app.gportal.service.impl;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.app.gportal.dto.CompanyMasterDTO;
import com.app.gportal.enums.ErrorCodeEnum;
import com.app.gportal.exceptions.DeleteNotAllowedException;
import com.app.gportal.exceptions.DuplicateDataException;
import com.app.gportal.exceptions.MandatoryFieldMissingException;
import com.app.gportal.exceptions.NotFoundException;
import com.app.gportal.model.AccountNameMaster;
import com.app.gportal.model.CompanyMaster;
import com.app.gportal.repository.IAccountNameMasterRepository;
import com.app.gportal.repository.ICompanyMasterRepository;
import com.app.gportal.response.CompanyMasterResponseDTO;
import com.app.gportal.response.MasterResponseTransactions;
import com.app.gportal.response.TransactionResponseDTO;
import com.app.gportal.service.ICompanyMasterService;
import com.app.gportal.utils.CommonUtils;

@Service
public class CompanyMasterService implements ICompanyMasterService{

	
private static final Logger log = LoggerFactory.getLogger(CompanyMasterService.class);
	
	@Autowired
	ICompanyMasterRepository companyMasterRepository;
	
	@Autowired
	IAccountNameMasterRepository accountNameMasterRepository;
	
	/**
	 * 
	 */
	@Override
	public CompanyMasterResponseDTO createCompanypMaster(CompanyMasterDTO companyMasterDTO) {
		log.info("In method createCompanypMaster for name=" + companyMasterDTO.getName());
			this.validateDataBeforeCreation(companyMasterDTO);
		    Instant instant = Instant.now(Clock.systemUTC());
			CompanyMaster companyMaster = new CompanyMaster();
			BeanUtils.copyProperties(companyMasterDTO, companyMaster);
			companyMaster.setCreatedAt(instant);
			companyMaster.setUpdatedAt(instant);
			CompanyMaster obj = this.companyMasterRepository.save(companyMaster);
			log.info("Company Master created successfully for name=" + companyMasterDTO.getName());
			return this.parseDBResponse(obj);
	}
	
	
	private void validateDataBeforeCreation(CompanyMasterDTO companyMasterDTO) {
		if (StringUtils.isEmpty(companyMasterDTO.getName())) {
			log.error("In method validateDataBeforeCreation. Field Name is empty");
			throw new MandatoryFieldMissingException(ErrorCodeEnum.GPORTAL_007);
		}
		long duplicateNameMatchCount = this.companyMasterRepository.countByName(companyMasterDTO.getName());
		if (duplicateNameMatchCount > 0) {
			log.error("In method validateDataBeforeCreation. Name is duplicate for name="
					+ companyMasterDTO.getName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_008);
		}
	}
	
	/**
	 * 
	 * @param companyMaster
	 * @return
	 */
	private CompanyMasterResponseDTO parseDBResponse(CompanyMaster companyMaster) {
		BigDecimal baseRate = companyMaster.getBaseRate() != null ? companyMaster.getBaseRate().setScale(2) : null;
		return CompanyMasterResponseDTO.builder().id(companyMaster.getId())
										  .name(companyMaster.getName())
										  .baseRate(baseRate)
										  .remarks(companyMaster.getRemarks())
										  .build();
	}
	
	
	/**
	 * 
	 */
	@Override
	public CompanyMasterResponseDTO modifyCompanypMaster(CompanyMasterDTO companyMasterDTO) {
		log.info("In method modifyCompanypMaster for name=" + companyMasterDTO.getName());
		this.validateDataBeforeUpdation(companyMasterDTO);
		Optional<CompanyMaster> existingCompany = this.companyMasterRepository.findById(companyMasterDTO.getId());
		if(!existingCompany.isPresent()) {
			log.error("Update request failed as company does not exist for name=" + companyMasterDTO.getName());
			throw new NotFoundException(ErrorCodeEnum.GPORTAL_003);
		}
		CompanyMaster companyMaster = existingCompany.get();
		companyMaster.setBaseRate(companyMasterDTO.getBaseRate());
		companyMaster.setName(companyMasterDTO.getName());
		companyMaster.setRemarks(companyMasterDTO.getRemarks());
		companyMaster.setUpdatedAt(Instant.now(Clock.systemUTC()));
		
		
		CompanyMaster obj = this.companyMasterRepository.save(companyMaster);
		log.info("HolderGroup updated successfully for name=" + companyMasterDTO.getName());
		return this.parseDBResponse(obj);
	}
	
	/**
	 * 
	 */
	@Override
	public List<CompanyMasterResponseDTO> getAllCompanyMaster(){
		log.info("In method getAllCompanyMaster::CompanyMasterService");
		List<CompanyMaster> companies = this.companyMasterRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
		List<CompanyMasterResponseDTO> companyMasterList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(companies)) {
			for(CompanyMaster companyMaster : companies) {
				companyMasterList.add(this.parseDBResponse(companyMaster));
			}
		}
		return companyMasterList;
	}
	
	private void validateDataBeforeUpdation(CompanyMasterDTO companyMasterDTO) {
		if (StringUtils.isEmpty(companyMasterDTO.getName())) {
			log.error("In method validateDataBeforeCreation. Field Name is empty");
			throw new MandatoryFieldMissingException(ErrorCodeEnum.GPORTAL_007);
		}
		CompanyMaster hgm1 = this.companyMasterRepository.findByName(companyMasterDTO.getName());
		if (hgm1 != null && hgm1.getId() != companyMasterDTO.getId()) {
			log.error("In method validateDataBeforeCreation. Name is duplicate for name="
					+ companyMasterDTO.getName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_008);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void deleteCompanyMaster(Long id) {
		log.info("In method deleteCompanyMaster for id=" + id);
		Optional<CompanyMaster> existingCompany = this.companyMasterRepository.findById(id);
		if(!existingCompany.isPresent()) {
			log.error("Deletion request failed as company does not exist for id=" + id);
			throw new NotFoundException(ErrorCodeEnum.GPORTAL_003);
		}
		if (!CollectionUtils.isEmpty(existingCompany.get().getAccountNameMaster())) {
			log.error("Company cannot be deleted as association exists");
			throw new DeleteNotAllowedException(ErrorCodeEnum.GPORTAL_012);
		}
		this.companyMasterRepository.deleteById(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public CompanyMasterResponseDTO getCompanyMaster(Long id) {
		log.info("In method getCompanyMaster for id=" + id);
		Optional<CompanyMaster> existingCompany = this.companyMasterRepository.findById(id);
		if(!existingCompany.isPresent()) {
			log.error("Find request failed as company does not exist for id=" + id);
			throw new NotFoundException(ErrorCodeEnum.GPORTAL_003);
		}
		CompanyMaster obj = existingCompany.get();
		return this.parseDBResponse(obj);
	}
	
	@Override
	public List<TransactionResponseDTO> getAllCompanyMasterNameAndTransactions(){
		log.info("In method getAllCompanyMasterNameAndTransactions");
		List<CompanyMaster> companyMasters = this.companyMasterRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
		List<TransactionResponseDTO> txnList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(companyMasters)) {
			int index = 0;
			for(CompanyMaster companyMaster : companyMasters) {
				TransactionResponseDTO transactionResponseDTO = TransactionResponseDTO.builder()
							.id(companyMaster.getId())
							.name(companyMaster.getName()).build();
				if(index == 0) {
					transactionResponseDTO.setTransactions(this.getTransactions(companyMaster));
					CommonUtils.calculateTotalBalance(transactionResponseDTO.getTransactions(), transactionResponseDTO);
				}
				txnList.add(transactionResponseDTO);
				index++;
			}
		}
		return txnList;
	}
	
	private CompanyMaster validateCompany(Long id) {
		Optional<CompanyMaster> existingCompany = this.companyMasterRepository.findById(id);
		if (!existingCompany.isPresent()) {
			log.error("Find request failed as company does not exist for id=" + id);
			throw new NotFoundException(ErrorCodeEnum.GPORTAL_003);
		}
		return existingCompany.get();
	}
	
	@Override
	public TransactionResponseDTO getCompanyTransactions(Long id) {
		log.info("In method getCompanyTransactions");
		
		CompanyMaster companyMaster = this.validateCompany(id);
		TransactionResponseDTO transactionResponseDTO = TransactionResponseDTO.builder().id(companyMaster.getId())
				.name(companyMaster.getName()).build();
		transactionResponseDTO.setTransactions(this.getTransactions(companyMaster));
		CommonUtils.calculateTotalBalance(transactionResponseDTO.getTransactions(), transactionResponseDTO);
		transactionResponseDTO.setLastSaved(CommonUtils.getLastSavedDate(companyMaster.getAccountNameMaster()));
		return transactionResponseDTO;
	}
	
	
	private List<MasterResponseTransactions> getTransactions(CompanyMaster companyMaster) {
		if(!CollectionUtils.isEmpty(companyMaster.getAccountNameMaster())) {
			return CommonUtils.getTransactionList(companyMaster.getAccountNameMaster());
		}
		return null;
	}
	
	@Override
	public void clearTransactions(Long id) {
		log.info("In method clearTransactions::AccountNameMasterService");
		CompanyMaster companyMaster = this.validateCompany(id);
		if(!CollectionUtils.isEmpty(companyMaster.getAccountNameMaster())){
			List<AccountNameMaster> accountNameMasters = new ArrayList<>();
			for(AccountNameMaster accountNameMaster : companyMaster.getAccountNameMaster())
			{
				accountNameMasters.add(CommonUtils.updateAccountNameMaster(accountNameMaster, new Double(0)));
			}
			this.accountNameMasterRepository.saveAll(accountNameMasters);
		}
	}
	
}
