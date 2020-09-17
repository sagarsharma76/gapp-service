package com.app.gportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.gportal.model.CompanyMaster;

@Repository
public interface ICompanyMasterRepository extends JpaRepository<CompanyMaster, Long>{

	public long countByName(String name);
	
	public CompanyMaster findByName(String name);
}
