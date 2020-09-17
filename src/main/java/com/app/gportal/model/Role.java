package com.app.gportal.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -8913133874335070325L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;

	@Column(name="edit_access")
	private boolean editAccess;
	
	@Column(name="admin_access")
	private String adminAccess;

	@Column(name="read_access")
	private String readAccess;
	
	@Column(name="created_at")
	private Instant createdAt;
	
	@Column(name="updatedAt")
	private Instant updatedAt;

}
