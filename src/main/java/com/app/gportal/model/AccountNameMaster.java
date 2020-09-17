package com.app.gportal.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_name_master")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountNameMaster implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 6857077328170261144L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="company_master_id", nullable = true)
	private CompanyMaster companyMaster;
	
	@ManyToOne
	@JoinColumn(name="account_holder_master_id")
	private AccountHolderMaster accountHolderMaster;
	
	@Column(name="rate")
	private Double rate;
	
	@Column(name="base_amount")
	private Double baseAmount;
	
	@Column(name="o_balance")
	private Double oBalance;
	
	@Column(name="balance")
	private Double balance;
	
	@Column(name="point_pnl")
	private Double pointPnl;
	
	@Column(name="profit_loss")
	private Double profitLoss;
	
	@Column(name="profit")
	private Boolean profit;
	
	@Column(name="status_id")
	private Long statusId;
	
	@Column(name="active")
	private Boolean active;
	
	@Column(name="created_at")
	private Instant createdAt;
	
	@Column(name="updated_at")
	private Instant updatedAt;
	
}
