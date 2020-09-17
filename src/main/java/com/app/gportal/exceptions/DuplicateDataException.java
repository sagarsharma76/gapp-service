package com.app.gportal.exceptions;

import com.app.gportal.enums.ErrorCodeEnum;

public class DuplicateDataException extends GportalRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1755307385350613575L;

	/**
	 * 
	 */

	public DuplicateDataException(ErrorCodeEnum errorCode) {
		super(errorCode);
	}

}
