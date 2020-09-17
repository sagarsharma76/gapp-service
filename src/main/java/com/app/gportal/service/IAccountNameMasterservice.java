package com.app.gportal.service;

import java.util.List;

import com.app.gportal.dto.AccountNameMasterDTO;
import com.app.gportal.dto.TransactionRequestDTO;
import com.app.gportal.response.AccountNameMasterResponseDTO;
import com.app.gportal.response.MasterResponseTransactions;

public interface IAccountNameMasterservice {

	public AccountNameMasterResponseDTO createAccountNameMaster(AccountNameMasterDTO accountNameMasterDTO);
	
	public AccountNameMasterResponseDTO modifyAccountNameMaster(AccountNameMasterDTO accountNameMasterDTO);
	
	public void deleteAccountNameMaster(Long id);
	
	public AccountNameMasterResponseDTO getAccountNameMaster(Long id);
	
	public List<AccountNameMasterResponseDTO> getAllAccountNamerMaster();
	
	public MasterResponseTransactions updateTransaction(Long id, TransactionRequestDTO txnRequest);
}
