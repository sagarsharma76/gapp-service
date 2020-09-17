package com.app.gportal.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "holder_group_master")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HolderGroupMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2084563689927665588L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="active")
	private Boolean active;
	
	@Column(name="created_at")
	private Instant createdAt;
	
	@Column(name="updated_at")
	private Instant updatedAt;
	
	@OneToMany(mappedBy = "holderGroupMaster")
	private List<AccountHolderMaster> accountHolderMaster;
	
}
