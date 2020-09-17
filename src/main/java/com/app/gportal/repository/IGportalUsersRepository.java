package com.app.gportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.gportal.model.GPortalUser;

@Repository
public interface IGportalUsersRepository extends JpaRepository<GPortalUser, Long>{

	public GPortalUser findByUserName(String userName);
	
	public long countByUserName(String userName);
}
