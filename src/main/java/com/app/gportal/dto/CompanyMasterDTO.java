package com.app.gportal.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyMasterDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8297351581170477794L;
	private Long id;
	private String name;
	private BigDecimal baseRate;
	private String remarks;
}
