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
public class JwtRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5789981501660280296L;
	private String userName;
	private String password;

//need default constructor for JSON Parsing
}
