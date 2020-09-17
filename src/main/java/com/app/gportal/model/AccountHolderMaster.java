package com.app.gportal.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_holder_master")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountHolderMaster implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1439998277606859417L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="status_id")
	private Long statusId;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@ManyToOne
	@JoinColumn(name="holder_group_master_id")
	private HolderGroupMaster holderGroupMaster;

	@Column(name="remarks")
	private String remarks;
	
	@Column(name="active")
	private Boolean active;
	
	@Column(name="created_at")
	private Instant createdAt;
	
	@Column(name="updated_at")
	private Instant updatedAt;
	
	@OneToMany(mappedBy = "accountHolderMaster")
	@OrderBy("name")
	private List<AccountNameMaster> accountNameMaster;
	
}
