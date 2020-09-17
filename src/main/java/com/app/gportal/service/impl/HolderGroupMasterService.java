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

import com.app.gportal.dto.HolderGroupMasterDTO;
import com.app.gportal.enums.ErrorCodeEnum;
import com.app.gportal.exceptions.DeleteNotAllowedException;
import com.app.gportal.exceptions.DuplicateDataException;
import com.app.gportal.exceptions.MandatoryFieldMissingException;
import com.app.gportal.exceptions.NotFoundException;
import com.app.gportal.model.HolderGroupMaster;
import com.app.gportal.repository.IHolderGroupMasterRepository;
import com.app.gportal.response.HolderGroupMasterResponseDTO;
import com.app.gportal.service.IHolderGroupMasterService;

@Service
public class HolderGroupMasterService implements IHolderGroupMasterService{

	private static final Logger log = LoggerFactory.getLogger(HolderGroupMasterService.class);
	
	@Autowired
	IHolderGroupMasterRepository holderGroupMasterRepository;
	
	@Override
	public HolderGroupMasterResponseDTO createHolderGroupMaster(HolderGroupMasterDTO holderGroupMasterDTO) {
		 log.info("In method createHolderGroupMaster for username=" + holderGroupMasterDTO.getUserName());
		 this.validateDataBeforeCreation(holderGroupMasterDTO);
		 HolderGroupMaster holderGroupMaster = HolderGroupMaster.builder().build();
		 holderGroupMaster = this.mapData(holderGroupMaster, holderGroupMasterDTO);
		 holderGroupMaster.setCreatedAt(Instant.now(Clock.systemUTC()));
		 holderGroupMaster = this.holderGroupMasterRepository.save(holderGroupMaster);
		 return this.parseDataFromDB(holderGroupMaster);
	}
	
	private HolderGroupMaster mapData(HolderGroupMaster holderGroupMaster, HolderGroupMasterDTO holderGroupMasterDTO) {
		holderGroupMaster.setName(holderGroupMasterDTO.getName());
		holderGroupMaster.setUserName(holderGroupMasterDTO.getUserName());
		holderGroupMaster.setPassword(holderGroupMasterDTO.getPassword());
		holderGroupMaster.setRemarks(holderGroupMasterDTO.getRemarks());
		holderGroupMaster.setActive(true);
		holderGroupMaster.setUpdatedAt(Instant.now(Clock.systemUTC()));
		return holderGroupMaster;
	}
	
	private void validateDataBeforeCreation(HolderGroupMasterDTO holderGroupMasterDTO) {
		if (StringUtils.isEmpty(holderGroupMasterDTO.getName())) {
			log.error("In method validateDataBeforeCreation. Field Name is empty");
			throw new MandatoryFieldMissingException(ErrorCodeEnum.GPORTAL_007);
		}
		long duplicateNameMatchCount = this.holderGroupMasterRepository.countByName(holderGroupMasterDTO.getName());
		if (duplicateNameMatchCount > 0) {
			log.error("In method validateDataBeforeCreation. Name is duplicate for name="
					+ holderGroupMasterDTO.getName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_008);
		}
		long duplicateUserNameMatchCount = this.holderGroupMasterRepository
				.countByUserName(holderGroupMasterDTO.getUserName());
		if (duplicateUserNameMatchCount > 0) {
			log.error("In method validateDataBeforeCreation. userName is duplicate for userName="
					+ holderGroupMasterDTO.getUserName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_002);
		}
	}
	
	private void validateDataBeforeUpdation(HolderGroupMasterDTO holderGroupMasterDTO) {
		if (StringUtils.isEmpty(holderGroupMasterDTO.getName())) {
			log.error("In method validateDataBeforeCreation. Field Name is empty");
			throw new MandatoryFieldMissingException(ErrorCodeEnum.GPORTAL_007);
		}
		HolderGroupMaster hgm1 = this.holderGroupMasterRepository.findByName(holderGroupMasterDTO.getName());
		if (hgm1 != null && hgm1.getId() != holderGroupMasterDTO.getId()) {
			log.error("In method validateDataBeforeCreation. Name is duplicate for name="
					+ holderGroupMasterDTO.getName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_008);
		}
		HolderGroupMaster hgm2 = this.holderGroupMasterRepository.findByUserName(holderGroupMasterDTO.getUserName());
		if (hgm2 != null && hgm2.getId() != holderGroupMasterDTO.getId()) {
			log.error("In method validateDataBeforeCreation. userName is duplicate for userName="
					+ holderGroupMasterDTO.getUserName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_002);
		}
	}
	
	private HolderGroupMasterResponseDTO parseDataFromDB(HolderGroupMaster holderGroupMaster) {
		return HolderGroupMasterResponseDTO.builder()
				.id(holderGroupMaster.getId())
				.name(holderGroupMaster.getName())
				.userName(holderGroupMaster.getUserName())
				.password(holderGroupMaster.getPassword())
				.remarks(holderGroupMaster.getRemarks())
				.build();
	}
	
	private HolderGroupMaster validateExistingRecord(Long id) {
		log.info("In method validateExistingRecord for id=" + id);
		Optional<HolderGroupMaster> holderGroupMasterOpt = this.holderGroupMasterRepository.findById(id);
		if(!holderGroupMasterOpt.isPresent()) {
			log.error("In method validateExistingRecord. Holder Group master does not exists with id=" + id);
			throw new NotFoundException(ErrorCodeEnum.GPORTAL_009);
		}
		return holderGroupMasterOpt.get();
	}
	
	@Override
	public HolderGroupMasterResponseDTO modifyHolderGroupMaster(HolderGroupMasterDTO holderGroupMasterDTO) {
		log.info("In method modifyHolderGroupMaster for id=" + holderGroupMasterDTO.getId());
		this.validateDataBeforeUpdation(holderGroupMasterDTO);
		HolderGroupMaster holderGroupMaster = this.validateExistingRecord(holderGroupMasterDTO.getId());
		holderGroupMaster = this.mapData(holderGroupMaster, holderGroupMasterDTO);
		holderGroupMaster = this.holderGroupMasterRepository.save(holderGroupMaster);
		return this.parseDataFromDB(holderGroupMaster);
	}
	
	@Override
	public void deleteHolderGroupMaster(Long id) {
		log.info("In method deleteHolderGroupMaster for id=" + id);
		HolderGroupMaster holderGroupMaster = this.validateExistingRecord(id);
		if(holderGroupMaster != null && !CollectionUtils.isEmpty(holderGroupMaster.getAccountHolderMaster())) {
			log.error("Holder group master cannot be deleted as association exists");
			throw new DeleteNotAllowedException(ErrorCodeEnum.GPORTAL_011);
		}
		this.holderGroupMasterRepository.deleteById(id);
	}
	
	@Override
	public HolderGroupMasterResponseDTO getHolderGroupMaster(Long id) {
		log.info("In method getHolderGroupMaster for id=" + id);
		Optional<HolderGroupMaster> holderGroupMasterOpt = this.holderGroupMasterRepository.findById(id);
		if(holderGroupMasterOpt.isPresent()) {
			return this.parseDataFromDB(holderGroupMasterOpt.get());
		}
		return null;
	}
	
	@Override
	public List<HolderGroupMasterResponseDTO> getAllHolderGrouprMaster(){
		log.info("In method getAllHolderGrouprMaster");
		List<HolderGroupMasterResponseDTO> accounts = new ArrayList<>();
		List<HolderGroupMaster> dbAccounts = this.holderGroupMasterRepository.findAll();
		if(!CollectionUtils.isEmpty(dbAccounts)) {
			for(HolderGroupMaster accountHolderMaster : dbAccounts) {
				accounts.add(this.parseDataFromDB(accountHolderMaster));
			}
		}
		return accounts;
	}
	
}
