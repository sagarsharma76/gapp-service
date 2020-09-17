package com.app.gportal.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolderMasterDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -7669265195469679202L;
	
	
	private Long id;
	
	private String name;
	
	private String mobileNumber;
	
	private Long statusId;
	
	private String userName;
	
	private String password;
	
	private Long holderGroupMasterId;
	
	private String remarks;

}
