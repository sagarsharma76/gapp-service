package com.app.gportal.service.impl;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.app.gportal.dto.GPortalUsersDTO;
import com.app.gportal.enums.ErrorCodeEnum;
import com.app.gportal.exceptions.DuplicateDataException;
import com.app.gportal.exceptions.MandatoryFieldMissingException;
import com.app.gportal.exceptions.NotFoundException;
import com.app.gportal.model.GPortalUser;
import com.app.gportal.model.Role;
import com.app.gportal.repository.IGportalUsersRepository;
import com.app.gportal.repository.IRoleRepository;
import com.app.gportal.response.GPortalUsersResponseDTO;
import com.app.gportal.service.IGportalUserService;

@Service
public class GPortalUsersService implements IGportalUserService {

	private static final Logger log = LoggerFactory.getLogger(GPortalUsersService.class);

	@Autowired
	private IGportalUsersRepository gportalUserRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public GPortalUsersResponseDTO createGPortalUser(GPortalUsersDTO gportalUserDTO) {
		log.info("In method createGPortalUser for username=" + gportalUserDTO.getUserName());
		this.validateDataBeforeCreation(gportalUserDTO);
		GPortalUser gportalUser = GPortalUser.builder().build();
		gportalUser = this.mapData(gportalUser, gportalUserDTO);
		gportalUser.setCreatedAt(Instant.now(Clock.systemUTC()));
		gportalUser = this.gportalUserRepository.save(gportalUser);
		return this.parseDataFromDB(gportalUser);
	}

	private GPortalUser mapData(GPortalUser gportalUser, GPortalUsersDTO gportalUserDTO) {

		gportalUser.setUserName(gportalUserDTO.getUserName());
		gportalUser.setPassword(this.getPassword(gportalUserDTO.getPassword()));
		gportalUser.setName(gportalUserDTO.getName());
		gportalUser.setMobile(gportalUserDTO.getMobile());
		gportalUser.setRemarks(gportalUserDTO.getRemarks());
		gportalUser.setRole(this.validateRole(gportalUserDTO));
		gportalUser.setUpdatedAt(Instant.now(Clock.systemUTC()));
		return gportalUser;
	}
	
	private String getPassword(String password) {
		String salt = BCrypt.gensalt(10);
		return BCrypt.hashpw(password, salt);
	}

	private Role validateRole(GPortalUsersDTO gportalUserDTO) {
		Optional<Role> roleOpt = this.roleRepository.findById(gportalUserDTO.getRoleId());
		if (!roleOpt.isPresent()) {
			log.error("Selected role does not exists for roleId=" + gportalUserDTO.getRoleId());
			throw new NotFoundException(ErrorCodeEnum.GPORTAL_015);
		}
		return roleOpt.get();
	}

	private GPortalUsersResponseDTO parseDataFromDB(GPortalUser gportalUser) {
		return GPortalUsersResponseDTO.builder().id(gportalUser.getId()).userName(gportalUser.getUserName())
				.password(gportalUser.getPassword()).name(gportalUser.getName()).mobile(gportalUser.getMobile())
				.roleId(gportalUser.getRole().getId())
				.remarks(gportalUser.getRemarks()).build();
	}

	private void validateDataBeforeCreation(GPortalUsersDTO gportalUserDTO) {
		if (StringUtils.isEmpty(gportalUserDTO.getUserName())) {
			log.error("In method validateDataBeforeCreation. Field User Name is empty");
			throw new MandatoryFieldMissingException(ErrorCodeEnum.GPORTAL_014);
		}
		long duplicateUserNameMatchCount = this.gportalUserRepository.countByUserName(gportalUserDTO.getUserName());
		if (duplicateUserNameMatchCount > 0) {
			log.error("In method validateDataBeforeCreation. userName is duplicate for userName="
					+ gportalUserDTO.getUserName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_002);
		}

	}

	private void validateDataBeforeUpdation(GPortalUsersDTO gPortalUsersDTO) {
		if (StringUtils.isEmpty(gPortalUsersDTO.getUserName())) {
			log.error("In method validateDataBeforeCreation. Field UserName is empty");
			throw new MandatoryFieldMissingException(ErrorCodeEnum.GPORTAL_014);
		}
		GPortalUser gPortalUser1 = this.gportalUserRepository.findByUserName(gPortalUsersDTO.getUserName());
		if (gPortalUser1 != null && gPortalUser1.getId() != gPortalUsersDTO.getId()) {
			log.error("In method validateDataBeforeUpdation. UserName is duplicate for username="
					+ gPortalUsersDTO.getUserName());
			throw new DuplicateDataException(ErrorCodeEnum.GPORTAL_008);
		}
	}

	@Override
	public GPortalUsersResponseDTO modifyGPortalUser(GPortalUsersDTO gPortalUsersDTO) {
		log.info("In method modifyGPortalUser for id=" + gPortalUsersDTO.getId());
		this.validateDataBeforeUpdation(gPortalUsersDTO);
		GPortalUser gPortalUser = this.validateExistingRecord(gPortalUsersDTO.getId());
		gPortalUser = this.mapData(gPortalUser, gPortalUsersDTO);
		gPortalUser = this.gportalUserRepository.save(gPortalUser);
		return this.parseDataFromDB(gPortalUser);
	}

	private GPortalUser validateExistingRecord(Long id) {
		log.info("In method validateExistingRecord for id=" + id);
		Optional<GPortalUser> gpoOptional = this.gportalUserRepository.findById(id);
		if (!gpoOptional.isPresent()) {
			log.error("In method validateExistingRecord. GPORTAL user does not exists with id=" + id);
			throw new NotFoundException(ErrorCodeEnum.GPORTAL_006);
		}
		return gpoOptional.get();
	}

	@Override
	public GPortalUsersResponseDTO getUser(Long id) {
		log.info("In method getUser for id=" + id);
		Optional<GPortalUser> gpoOptional = this.gportalUserRepository.findById(id);
		if (gpoOptional.isPresent()) {
			return this.parseDataFromDB(gpoOptional.get());
		}
		return null;
	}

	@Override
	public List<GPortalUsersResponseDTO> getAllUsers() {
		log.info("In method getAllUsers");
		List<GPortalUsersResponseDTO> accounts = new ArrayList<>();
		List<GPortalUser> users = this.gportalUserRepository.findAll();
		if (!CollectionUtils.isEmpty(users)) {
			for (GPortalUser gportalUser : users) {
				accounts.add(this.parseDataFromDB(gportalUser));
			}
		}
		return accounts;
	}
	
	@Override
	public void deleteUser(Long id) {
		log.info("In method deleteUser for id=" + id);
		this.validateExistingRecord(id);
		this.validateExistingRecord(id);
		this.gportalUserRepository.deleteById(id);
	}
	
	
	@Override
	public GPortalUsersResponseDTO loadUserByUsername(String userName) {
		GPortalUser gPortalUser = this.gportalUserRepository.findByUserName(userName);
		GPortalUsersResponseDTO gPortalUsersResponseDTO = null;
		if(gPortalUser != null) {
			gPortalUsersResponseDTO = this.parseDataFromDB(gPortalUser);
			List<GrantedAuthority> authorities
		      = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(gPortalUser.getRole().getName()));
		}
		return gPortalUsersResponseDTO;
	}

}
