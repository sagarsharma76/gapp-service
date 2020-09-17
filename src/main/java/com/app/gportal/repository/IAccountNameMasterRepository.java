package com.app.gportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.gportal.model.AccountNameMaster;

@Repository
public interface IAccountNameMasterRepository extends JpaRepository<AccountNameMaster, Long>{

	public long countByName(String name);
	
	public AccountNameMaster findByName(String name);
	
//	public void deleteAllByCompanyId(long companyId);
//	
//	public void deleteAllByAccountHolderMasterId(Long accountHolderMasterId);
	
}
