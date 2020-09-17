package com.app.gportal.config.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtResponse implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 3336294133231217969L;
	private String token;
	private boolean name;
	private boolean editAccess;
	

}
