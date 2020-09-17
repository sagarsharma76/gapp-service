package com.app.gportal.service;

import java.util.List;

import com.app.gportal.dto.HolderGroupMasterDTO;
import com.app.gportal.response.HolderGroupMasterResponseDTO;

public interface IHolderGroupMasterService {

	public HolderGroupMasterResponseDTO createHolderGroupMaster(HolderGroupMasterDTO holderGroupMasterDTO);
	
	public HolderGroupMasterResponseDTO modifyHolderGroupMaster(HolderGroupMasterDTO holderGroupMasterDTO);
	
	public void deleteHolderGroupMaster(Long id);
	
	public HolderGroupMasterResponseDTO getHolderGroupMaster(Long id);
	
	public List<HolderGroupMasterResponseDTO> getAllHolderGrouprMaster();
}
