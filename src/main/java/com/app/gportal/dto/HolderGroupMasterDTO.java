package com.app.gportal.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolderGroupMasterDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6932686166567881943L;
	
	private Long id;
	private String userName;
	private String name;
	private String password;
	private String remarks;

}
