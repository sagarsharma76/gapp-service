package com.app.gportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.gportal.model.AccountHolderMaster;

@Repository
public interface IAccountHolderMasterRepository extends JpaRepository<AccountHolderMaster, Long>{
	
	public long countByName(String name);
	
	public long countByUserName(String userName);
	
	public AccountHolderMaster findByUserName(String userName);
	
	public AccountHolderMaster findByName(String name);

}
