package com.app.gportal.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.gportal.dto.GPortalUsersDTO;
import com.app.gportal.response.GPortalUsersResponseDTO;

public interface IGportalUserService extends UserDetailsService{

	public GPortalUsersResponseDTO createGPortalUser(GPortalUsersDTO gportalUserDTO);
	
	public GPortalUsersResponseDTO modifyGPortalUser(GPortalUsersDTO gPortalUsersDTO);
	
	public GPortalUsersResponseDTO getUser(Long id);
	
	public List<GPortalUsersResponseDTO> getAllUsers();
	
	public void deleteUser(Long id);
	
}
