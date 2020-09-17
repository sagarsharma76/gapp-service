package com.app.gportal.service;

import java.util.List;

import com.app.gportal.dto.CompanyMasterDTO;
import com.app.gportal.response.CompanyMasterResponseDTO;
import com.app.gportal.response.TransactionResponseDTO;

public interface ICompanyMasterService {

	public CompanyMasterResponseDTO createCompanypMaster(CompanyMasterDTO companyMasterDTO);
	
	public CompanyMasterResponseDTO modifyCompanypMaster(CompanyMasterDTO companyMasterDTO);
	
	public List<CompanyMasterResponseDTO> getAllCompanyMaster();
	
	public void deleteCompanyMaster(Long id);
	
	public CompanyMasterResponseDTO getCompanyMaster(Long id);
	
	public List<TransactionResponseDTO> getAllCompanyMasterNameAndTransactions();
	
	public TransactionResponseDTO getCompanyTransactions(Long id);
	
	public void clearTransactions(Long id);
}
