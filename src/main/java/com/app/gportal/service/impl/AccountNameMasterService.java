package com.app.gportal.service.impl;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.app.gportal.dto.AccountNameMasterDTO;
import com.app.gportal.dto.TransactionRequestDTO;
import com.app.gportal.enums.ErrorCodeEnum;
import com.app.gportal.exceptions.DuplicateDataException;
import com.app.gportal.exceptions.MandatoryFieldMissingException;
import com.app.gportal.exceptions.NotFoundException;
import com.app.gportal.model.AccountHolderMaster;
import com.app.gportal.model.AccountNameMaster;
import com.app.gportal.model.CompanyMaster;
import com.app.gportal.repository.IAccountHolderMasterRepository;
import com.app.gportal.repository.IAccountNameMasterRepository;
import com.app.gportal.repository.ICompanyMasterRepository;
import com.app.gportal.response.AccountNameMasterResponseDTO;
import com.app.gportal.response.MasterResponseTransactions;
import com.app.gportal.service.IAccountNameMasterservice;
import com.app.gportal.utils.CommonUtils;

@Service
public class AccountNameMasterService implements IAccountNameMasterservice {
	
	private static final Logger log = LoggerFactory.getLogger(AccountNameMasterService.class);
	
	@Autowired
	IAccountNameMasterRepository accountNameMasterRepository;
	
	@Autowired
	private ICompanyMasterRepository companyMasterRepository;
	
	@Autowired
	private IAccountHolderMasterRepository accountHolderMasterRepository;

	@Override
	public AccountNameMasterResponseDTO createAccountNameMaster(AccountNameMasterDTO accountNameMasterDTO) {
		 log.info("In method createAccountNameMaster for name=" + accountNameMasterDTO.getName());
		 this.validateDataBeforeCreation(accountNameMasterDTO);
		 AccountNameMaster accountNameMaster = AccountNameMaster.builder().build();
		 accountNameMaster = this.mapData(accountNameMaster, accountNameMasterDTO);
		 accountNameMaster.setCreatedAt(Instant.now(Clock.systemUTC()));
		 accountNameMaster = this.accountNameMasterRepository.save(accountNameMaster);
		 return this.parseDataFromDB(accountNameMaster);
	}
	
	private void validateDataBeforeCreation(AccountNameMasterDTO accountNameMasterDTO) {
		if (StringUtils.isEmpty(accountNameMasterDTO.getName())) {
			log.error("In method validateDataBeforeCreation. Field Name is empty");
			throw new MandatoryFieldMissingException(ErrorCodeEnum.GPORTAL_007);
		}
		long duplicateNameMatchCount = this.accountNameMasterRepository.countByName(accountNameMasterDTO.getName());
		if (duplicateNameMatchCount > 0) {
			log.error("In method validateDataBeforeCreation. Name is duplicate for name="
					+ accountNameMasterDTO.getName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_008);
		}
	}
	
	private AccountNameMasterResponseDTO parseDataFromDB(AccountNameMaster accountNameMaster) {
		return AccountNameMasterResponseDTO.builder()
				.id(accountNameMaster.getId())
				.name(accountNameMaster.getName())
				.rate(accountNameMaster.getRate())
				.baseAmount(accountNameMaster.getBaseAmount())
				.statusId(accountNameMaster.getStatusId())
				.accountHolderMasterId(accountNameMaster.getAccountHolderMaster() != null ? accountNameMaster.getAccountHolderMaster().getId() : null)
				.companyId(accountNameMaster.getCompanyMaster() != null ? accountNameMaster.getCompanyMaster().getId() : null)
				.build();
		
	}
	
	private AccountNameMaster mapData(AccountNameMaster accountNameMaster, AccountNameMasterDTO accountNameMasterDTO) {
		accountNameMaster.setName(accountNameMasterDTO.getName());
		accountNameMaster.setRate(accountNameMasterDTO.getRate());
		accountNameMaster.setBaseAmount(accountNameMasterDTO.getBaseAmount());
		accountNameMaster.setStatusId(accountNameMasterDTO.getStatusId());
		accountNameMaster.setUpdatedAt(Instant.now(Clock.systemUTC()));
		accountNameMaster.setAccountHolderMaster(this.getAccountHolderMaster(accountNameMasterDTO));
		accountNameMaster.setCompanyMaster(this.getCompanyMaster(accountNameMasterDTO));
		return accountNameMaster;
	}
	
	private CompanyMaster getCompanyMaster(AccountNameMasterDTO accountNameMasterDTO) {
		if(accountNameMasterDTO.getCompanyId() != null) {
			Optional<CompanyMaster> companyMasterOpt = this.companyMasterRepository.findById(accountNameMasterDTO.getCompanyId());
			if(companyMasterOpt.isPresent()) {
				return companyMasterOpt.get();
			}
		}
		return null;
	}
	
	private AccountHolderMaster getAccountHolderMaster(AccountNameMasterDTO accountNameMasterDTO) {
		if(accountNameMasterDTO.getCompanyId() != null) {
			Optional<AccountHolderMaster> accountHolderMasterOpt = this.accountHolderMasterRepository.findById(accountNameMasterDTO.getAccountHolderMasterId());
			if(accountHolderMasterOpt.isPresent()) {
				return accountHolderMasterOpt.get();
			}
		}
		return null;
	}

	@Override
	public AccountNameMasterResponseDTO modifyAccountNameMaster(AccountNameMasterDTO accountNameMasterDTO) {
		log.info("In method modifyAccountNameMaster for id=" + accountNameMasterDTO.getId());
		this.validateDataBeforeUpdation(accountNameMasterDTO);
		AccountNameMaster accountNameMaster = this.validateExistingRecord(accountNameMasterDTO.getId());
		accountNameMaster = this.mapData(accountNameMaster, accountNameMasterDTO);
		accountNameMaster = this.accountNameMasterRepository.save(accountNameMaster);
		return this.parseDataFromDB(accountNameMaster);
	}
	
	private void validateDataBeforeUpdation(AccountNameMasterDTO accountNameMasterDTO) {
		if (StringUtils.isEmpty(accountNameMasterDTO.getName())) {
			log.error("In method validateDataBeforeCreation. Field Name is empty");
			throw new MandatoryFieldMissingException(ErrorCodeEnum.GPORTAL_007);
		}
		AccountNameMaster hgm1 = this.accountNameMasterRepository.findByName(accountNameMasterDTO.getName());
		if (hgm1 != null && hgm1.getId() != accountNameMasterDTO.getId()) {
			log.error("In method validateDataBeforeCreation. Name is duplicate for name="
					+ accountNameMasterDTO.getName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_008);
		}
	}
	
	private AccountNameMaster validateExistingRecord(Long id) {
		log.info("In method validateExistingRecord for id=" + id);
		Optional<AccountNameMaster> accountHolderMasterOpt = this.accountNameMasterRepository.findById(id);
		if(!accountHolderMasterOpt.isPresent()) {
			log.error("In method validateExistingRecord. Account Name master does not exists with id=" + id);
			throw new NotFoundException(ErrorCodeEnum.GPORTAL_010);
		}
		return accountHolderMasterOpt.get();
	}

	@Override
	public void deleteAccountNameMaster(Long id) {
		log.info("In method deleteAccountNameMaster for id=" + id);
		this.validateExistingRecord(id);
		this.accountNameMasterRepository.deleteById(id);

	}

	@Override
	public AccountNameMasterResponseDTO getAccountNameMaster(Long id) {
		log.info("In method getAccountNameMaster for id=" + id);
		Optional<AccountNameMaster> accountNameMasterOpt = this.accountNameMasterRepository.findById(id);
		if(accountNameMasterOpt.isPresent()) {
			return this.parseDataFromDB(accountNameMasterOpt.get());
		}
		return null;
	}

	@Override
	public List<AccountNameMasterResponseDTO> getAllAccountNamerMaster() {
		log.info("In method getAllAccountNamerMaster");
		List<AccountNameMasterResponseDTO> accounts = new ArrayList<>();
		List<AccountNameMaster> dbAccounts = this.accountNameMasterRepository.findAll();
		if(!CollectionUtils.isEmpty(dbAccounts)) {
			for(AccountNameMaster accountNameMaster : dbAccounts) {
				accounts.add(this.parseDataFromDB(accountNameMaster));
			}
		}
		return accounts;
	}
	
	@Override
	public MasterResponseTransactions updateTransaction(Long id, TransactionRequestDTO txnRequest) {
		log.info("In method getAccountNameMaster for id=" + id);
		AccountNameMaster accountNameMaster = this.validateExistingRecord(id);
		accountNameMaster = CommonUtils.updateAccountNameMaster(accountNameMaster, txnRequest.getBalance());
		accountNameMaster = this.accountNameMasterRepository.save(accountNameMaster);
		return CommonUtils.getTransaction(accountNameMaster);
	}
	
}
