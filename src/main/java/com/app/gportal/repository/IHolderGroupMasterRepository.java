package com.app.gportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.gportal.model.HolderGroupMaster;

@Repository
public interface IHolderGroupMasterRepository extends JpaRepository<HolderGroupMaster, Long>{

	
	public long countByName(String name);
	
	public long countByUserName(String userName);
	
	public HolderGroupMaster findByUserName(String userName);
	
	public HolderGroupMaster findByName(String name);
}
