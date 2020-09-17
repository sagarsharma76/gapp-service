package com.app.gportal.exceptions;

import com.app.gportal.enums.ErrorCodeEnum;

public class MandatoryFieldMissingException extends GportalRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1226652515558827943L;

	/**
	 * 
	 */

	public MandatoryFieldMissingException(ErrorCodeEnum errorCode) {
		super(errorCode);
	}

}
