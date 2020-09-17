package com.app.gportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company_master")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyMaster implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2476106092954054244L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;

	@Column(name="base_rate")
	private BigDecimal baseRate;
	
	@Column(name="remarks")
	private String remarks;

	@Column(name="active")
	private Boolean active;
	
	@Column(name="created_at")
	private Instant createdAt;
	
	@Column(name="updatedAt")
	private Instant updatedAt;
	
	@OneToMany(mappedBy = "companyMaster")
	@OrderBy("name")
	private List<AccountNameMaster> accountNameMaster;
	
}
