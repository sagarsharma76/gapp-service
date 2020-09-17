package com.app.gportal.service;

import java.util.List;

import com.app.gportal.dto.AccountHolderMasterDTO;
import com.app.gportal.response.AccountHolderMasterResponseDTO;
import com.app.gportal.response.TransactionResponseDTO;

public interface IAccountHolderMasterService {

	public AccountHolderMasterResponseDTO createAccountHolderMaster(AccountHolderMasterDTO accountHolderMasterDTO);
	
	public AccountHolderMasterResponseDTO modifyAccountHolderMater(AccountHolderMasterDTO accountHolderMasterDTO);
	
	public void deleteAccountHolderMaster(Long id);
	
	public AccountHolderMasterResponseDTO getAccountHolderMaster(Long id);
	
	public List<AccountHolderMasterResponseDTO> getAllAccountHolderMaster();
	
	public TransactionResponseDTO getAccountHolderNameTransactions(Long id);
	
	public void clearTransactions(Long id);
}
