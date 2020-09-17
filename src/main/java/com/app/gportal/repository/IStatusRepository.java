package com.app.gportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.gportal.model.Status;

@Repository
public interface IStatusRepository extends JpaRepository<Status, Long>{

}
