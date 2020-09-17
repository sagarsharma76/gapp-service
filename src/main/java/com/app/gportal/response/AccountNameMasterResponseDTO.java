package com.app.gportal.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountNameMasterResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 200824079269410275L;

	private Long id;
	
	private String name;
	
	private Long companyId;
	
	private Long accountHolderMasterId;
	
	private Double rate;
	
	private Double baseAmount;
	
	private Long statusId;
	
}
