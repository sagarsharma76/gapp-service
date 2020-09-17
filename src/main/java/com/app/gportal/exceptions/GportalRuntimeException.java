package com.app.gportal.exceptions;

import org.springframework.http.HttpStatus;

import com.app.gportal.enums.ErrorCodeEnum;

import lombok.Getter;

@Getter
public class GportalRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4786434290003670326L;

	private ErrorCodeEnum errorCode;
	private HttpStatus httpStatus;
	
	public GportalRuntimeException(ErrorCodeEnum errorCode, HttpStatus httpStatus, Throwable e) {
		super(errorCode.getDescription(), e);
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}
	
	public GportalRuntimeException(ErrorCodeEnum errorCode,String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}
	
	public GportalRuntimeException(ErrorCodeEnum errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
	}

}
