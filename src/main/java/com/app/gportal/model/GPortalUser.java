package com.app.gportal.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gportalusers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GPortalUser implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 4775303981231485012L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username")
	private String userName;

	@Column(name="password")
	private String password;
	
	@Column(name="name")
	private String name;
	
	@OneToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;

	@Column(name="mobile")
	private String mobile;
	
	@Column(name="remarks")
	private String remarks;	
	
	@Column(name="created_at")
	private Instant createdAt;
	
	@Column(name="updatedAt")
	private Instant updatedAt;

}
