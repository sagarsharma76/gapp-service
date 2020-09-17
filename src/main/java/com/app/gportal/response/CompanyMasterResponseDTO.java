package com.app.gportal.response;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyMasterResponseDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 400097470945200769L;
	private Long id;
	private String name;
	private BigDecimal baseRate;
	private String remarks;
}
