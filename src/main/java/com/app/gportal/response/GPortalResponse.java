package com.app.gportal.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GPortalResponse<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8738883714590715507L;
	private boolean success;
	private List<ErrorResponse> errors;
	private T data;

}
