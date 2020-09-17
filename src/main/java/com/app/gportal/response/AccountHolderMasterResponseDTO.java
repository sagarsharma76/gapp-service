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
public class AccountHolderMasterResponseDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 8684621480613359576L;
	
	private Long id;
	private String name;
	private String mobileNumber;
	private Long statusId;
	private String userName;
	private String password;
	private Long holderGroupMasterId;
	private String remarks;
}
