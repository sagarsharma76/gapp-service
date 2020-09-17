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
public class HolderGroupMasterResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4551031537578347169L;

	private Long id;
	
	private String userName;

	private String password;
	
	private String name;
	
	private String remarks;
	
}
