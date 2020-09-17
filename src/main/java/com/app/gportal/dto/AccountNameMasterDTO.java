package com.app.gportal.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountNameMasterDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 3279984530349744760L;

	private Long id;
	
	private String name;
	
	private Long companyId;
	
	private Long accountHolderMasterId;
	
	private Double rate;
	
	private Double baseAmount;
	
	private Long statusId;
}
