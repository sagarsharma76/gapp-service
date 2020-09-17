package com.app.gportal.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
	
	private String errorCode;
	private String errorMessage;

}