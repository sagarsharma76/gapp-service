package com.app.gportal.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GPortalUsersDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7942565872926275427L;

	private Long id;
	
	private String userName;
	
	private String password;
	
	private String name;
	
	private Long roleId;
	
	private String mobile;
	
	private String remarks;
}
