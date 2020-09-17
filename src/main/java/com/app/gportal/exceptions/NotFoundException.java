package com.app.gportal.exceptions;

import com.app.gportal.enums.ErrorCodeEnum;

public class NotFoundException extends GportalRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8713878319817594067L;

	public NotFoundException(ErrorCodeEnum errorCode) {
		super(errorCode);
	}
}
