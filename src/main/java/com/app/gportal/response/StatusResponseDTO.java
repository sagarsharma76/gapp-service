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
public class StatusResponseDTO  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4234911239313950330L;
	/**
	 * 
	 */
	private Long id;
	private String name;

}
