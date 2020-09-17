package com.app.gportal.service.impl;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.app.gportal.dto.AccountHolderMasterDTO;
import com.app.gportal.enums.ErrorCodeEnum;
import com.app.gportal.exceptions.DeleteNotAllowedException;
import com.app.gportal.exceptions.DuplicateDataException;
import com.app.gportal.exceptions.MandatoryFieldMissingException;
import com.app.gportal.exceptions.NotFoundException;
import com.app.gportal.model.AccountHolderMaster;
import com.app.gportal.model.AccountNameMaster;
import com.app.gportal.model.HolderGroupMaster;
import com.app.gportal.repository.IAccountHolderMasterRepository;
import com.app.gportal.repository.IAccountNameMasterRepository;
import com.app.gportal.repository.IHolderGroupMasterRepository;
import com.app.gportal.response.AccountHolderMasterResponseDTO;
import com.app.gportal.response.MasterResponseTransactions;
import com.app.gportal.response.TransactionResponseDTO;
import com.app.gportal.service.IAccountHolderMasterService;
import com.app.gportal.utils.CommonUtils;

@Repository
public class AccountHolderMasterService implements IAccountHolderMasterService {

	
	@Autowired
	IAccountHolderMasterRepository accountHolderMasterRepository;
	
	@Autowired
	IAccountNameMasterRepository accountNameMasterRepository;
	
	@Autowired
	IHolderGroupMasterRepository holderGroupMasterRepository;
	
	private static final Logger log = LoggerFactory.getLogger(AccountHolderMasterService.class);
	
	@Override
	public AccountHolderMasterResponseDTO createAccountHolderMaster(AccountHolderMasterDTO accountHolderMasterDTO) {
		 log.info("In method createAccountHolderMaster for username=" + accountHolderMasterDTO.getUserName());
		 this.validateDataBeforeCreation(accountHolderMasterDTO);
		 AccountHolderMaster accountHolderMaster = AccountHolderMaster.builder().build();
		 accountHolderMaster = this.mapData(accountHolderMaster, accountHolderMasterDTO);
		 accountHolderMaster.setCreatedAt(Instant.now(Clock.systemUTC()));
		 accountHolderMaster = this.accountHolderMasterRepository.save(accountHolderMaster);
		 return this.parseDataFromDB(accountHolderMaster);
	}
	
	private AccountHolderMaster mapData(AccountHolderMaster accountHolderMaster, AccountHolderMasterDTO accountHolderMasterDTO) {
		accountHolderMaster.setName(accountHolderMasterDTO.getName());
		accountHolderMaster.setMobile(accountHolderMasterDTO.getMobileNumber());
		accountHolderMaster.setStatusId(accountHolderMasterDTO.getStatusId());
		accountHolderMaster.setUserName(accountHolderMasterDTO.getUserName());
		accountHolderMaster.setPassword(accountHolderMasterDTO.getPassword());
		accountHolderMaster.setRemarks(accountHolderMasterDTO.getRemarks());
		accountHolderMaster.setUpdatedAt(Instant.now(Clock.systemUTC()));
		accountHolderMaster.setHolderGroupMaster(this.getHolderGroupMaster(accountHolderMasterDTO));
		return accountHolderMaster;
	}
	
	
	private HolderGroupMaster getHolderGroupMaster(AccountHolderMasterDTO accountHolderMasterDTO) {
		if(accountHolderMasterDTO.getHolderGroupMasterId() != null) {
			Optional<HolderGroupMaster> holderGroupMasterOpt = this.holderGroupMasterRepository.findById(accountHolderMasterDTO.getHolderGroupMasterId());
			if(holderGroupMasterOpt.isPresent()) {
				return holderGroupMasterOpt.get();
			}
		}
		return null;
	}
	
	private void validateDataBeforeCreation(AccountHolderMasterDTO accountHolderMasterDTO) {
		if (StringUtils.isEmpty(accountHolderMasterDTO.getName())) {
			log.error("In method validateDataBeforeCreation. Field Name is empty");
			throw new MandatoryFieldMissingException(ErrorCodeEnum.GPORTAL_007);
		}
		long duplicateNameMatchCount = this.accountHolderMasterRepository.countByName(accountHolderMasterDTO.getName());
		if (duplicateNameMatchCount > 0) {
			log.error("In method validateDataBeforeCreation. Name is duplicate for name="
					+ accountHolderMasterDTO.getName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_008);
		}
		long duplicateUserNameMatchCount = this.accountHolderMasterRepository
				.countByUserName(accountHolderMasterDTO.getUserName());
		if (duplicateUserNameMatchCount > 0) {
			log.error("In method validateDataBeforeCreation. userName is duplicate for userName="
					+ accountHolderMasterDTO.getUserName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_002);
		}
	}
	
	private void validateDataBeforeUpdation(AccountHolderMasterDTO accountHolderMasterDTO) {
		if (StringUtils.isEmpty(accountHolderMasterDTO.getName())) {
			log.error("In method validateDataBeforeCreation. Field Name is empty");
			throw new MandatoryFieldMissingException(ErrorCodeEnum.GPORTAL_007);
		}
		AccountHolderMaster hgm1 = this.accountHolderMasterRepository.findByName(accountHolderMasterDTO.getName());
		if (hgm1 != null && hgm1.getId() != accountHolderMasterDTO.getId()) {
			log.error("In method validateDataBeforeCreation. Name is duplicate for name="
					+ accountHolderMasterDTO.getName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_008);
		}
		AccountHolderMaster hgm2 = this.accountHolderMasterRepository.findByUserName(accountHolderMasterDTO.getUserName());
		if (hgm2 != null && hgm2.getId() != accountHolderMasterDTO.getId()) {
			log.error("In method validateDataBeforeCreation. userName is duplicate for userName="
					+ accountHolderMasterDTO.getUserName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_002);
		}
	}
	
	private AccountHolderMasterResponseDTO parseDataFromDB(AccountHolderMaster accountHolderMaster) {
		return AccountHolderMasterResponseDTO.builder()
				.id(accountHolderMaster.getId())
				.name(accountHolderMaster.getName())
				.mobileNumber(accountHolderMaster.getMobile())
				.statusId(accountHolderMaster.getStatusId())
				.userName(accountHolderMaster.getUserName())
				.password(accountHolderMaster.getPassword())
				.holderGroupMasterId(accountHolderMaster.getHolderGroupMaster() != null ? accountHolderMaster.getHolderGroupMaster().getId() : null)
				.remarks(accountHolderMaster.getRemarks())
				.build();
	}
	
	
	@Override
	public AccountHolderMasterResponseDTO modifyAccountHolderMater(AccountHolderMasterDTO accountHolderMasterDTO) {
		log.info("In method createAccountHolderMaster for id=" + accountHolderMasterDTO.getId());
		this.validateDataBeforeUpdation(accountHolderMasterDTO);
		AccountHolderMaster accountHolderMaster = this.validateExistingRecord(accountHolderMasterDTO.getId());
		accountHolderMaster = this.mapData(accountHolderMaster, accountHolderMasterDTO);
		accountHolderMaster = this.accountHolderMasterRepository.save(accountHolderMaster);
		return this.parseDataFromDB(accountHolderMaster);
	}
	
	private AccountHolderMaster validateExistingRecord(Long id) {
		log.info("In method validateExistingRecord for id=" + id);
		Optional<AccountHolderMaster> accountHolderMasterOpt = this.accountHolderMasterRepository.findById(id);
		if(!accountHolderMasterOpt.isPresent()) {
			log.error("In method validateExistingRecord. Account Holder master does not exists with id=" + id);
			throw new NotFoundException(ErrorCodeEnum.GPORTAL_006);
		}
		return accountHolderMasterOpt.get();
	}
	
	
	@Override
	public void deleteAccountHolderMaster(Long id) {
		log.info("In method deleteAccountHolderMaster for id=" + id);
		this.validateExistingRecord(id);
		AccountHolderMaster accountHolderMaster = this.validateExistingRecord(id);
		if(accountHolderMaster != null && !CollectionUtils.isEmpty(accountHolderMaster.getAccountNameMaster())) {
			log.error("Account holder master cannot be deleted as association exists");
			throw new DeleteNotAllowedException(ErrorCodeEnum.GPORTAL_013);
		}
		this.accountHolderMasterRepository.deleteById(id);
	}
	
	@Override
	public AccountHolderMasterResponseDTO getAccountHolderMaster(Long id) {
		log.info("In method getAccountHolderMaster for id=" + id);
		Optional<AccountHolderMaster> accountHolderMasterOpt = this.accountHolderMasterRepository.findById(id);
		if(accountHolderMasterOpt.isPresent()) {
			return this.parseDataFromDB(accountHolderMasterOpt.get());
		}
		return null;
	}
	
	@Override
	public List<AccountHolderMasterResponseDTO> getAllAccountHolderMaster(){
		log.info("In method getAllAccountHolderMaster");
		List<AccountHolderMasterResponseDTO> accounts = new ArrayList<>();
		List<AccountHolderMaster> dbAccounts = this.accountHolderMasterRepository.findAll();
		if(!CollectionUtils.isEmpty(dbAccounts)) {
			for(AccountHolderMaster accountHolderMaster : dbAccounts) {
				accounts.add(this.parseDataFromDB(accountHolderMaster));
			}
		}
		return accounts;
	}
	
	@Override
	public TransactionResponseDTO getAccountHolderNameTransactions(Long id) {
		log.info("In method getAccountHolderNameTransactions");
		Optional<AccountHolderMaster> existingAccountHolder = this.accountHolderMasterRepository.findById(id);
		if (!existingAccountHolder.isPresent()) {
			log.error("Find request failed as account holder does not exist for id=" + id);
			throw new NotFoundException(ErrorCodeEnum.GPORTAL_003);
		}
		AccountHolderMaster accountHolderMaster = existingAccountHolder.get();
		TransactionResponseDTO transactionResponseDTO = TransactionResponseDTO.builder().id(accountHolderMaster.getId())
				.name(accountHolderMaster.getName()).build();
		transactionResponseDTO.setTransactions(this.getTransactions(accountHolderMaster));
		transactionResponseDTO.setTotalBalance(CommonUtils.calculateTotalBalance(transactionResponseDTO.getTransactions()));
		transactionResponseDTO.setLastSaved(CommonUtils.getLastSavedDate(accountHolderMaster.getAccountNameMaster()));
		return transactionResponseDTO;
	}
	
	
	private List<MasterResponseTransactions> getTransactions(AccountHolderMaster accountHolderMaster) {
		if(!CollectionUtils.isEmpty(accountHolderMaster.getAccountNameMaster())) {
			return CommonUtils.getTransactionList(accountHolderMaster.getAccountNameMaster());
		}
		return null;
	}
	
	@Override
	public void clearTransactions(Long id) {
		log.info("In method clearTransactions::AccountHolderMasterService");
		AccountHolderMaster accountHolderMaster = this.validateExistingRecord(id);
		if(!CollectionUtils.isEmpty(accountHolderMaster.getAccountNameMaster())){
			List<AccountNameMaster> accountNameMasters = new ArrayList<>();
			for(AccountNameMaster accountNameMaster : accountHolderMaster.getAccountNameMaster())
			{
				accountNameMasters.add(CommonUtils.updateAccountNameMaster(accountNameMaster, new Double(0)));
			}
			this.accountNameMasterRepository.saveAll(accountNameMasters);
		}
	}
	
	
}
