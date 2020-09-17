package com.app.gportal.exceptions;

import com.app.gportal.enums.ErrorCodeEnum;

public class DuplicateUserNameException extends GportalRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1205300201049222648L;

	/**
	 * 
	 */

	public DuplicateUserNameException(ErrorCodeEnum errorCode) {
		super(errorCode);
	}

}
