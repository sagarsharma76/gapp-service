package com.app.gportal.exceptions;

import com.app.gportal.enums.ErrorCodeEnum;

public class DeleteNotAllowedException extends GportalRuntimeException{

	/**
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -1607350194335142701L;

	/**
	 * 
	 */

	public DeleteNotAllowedException(ErrorCodeEnum errorCode) {
		super(errorCode);
	}

}