package com.app.gportal.enums;

public enum ErrorCodeEnum {

	GPORTAL_002("User Name already exists"),
	GPORTAL_003("Company Master does not exists"),
	GPORTAL_004("Holder Group Master does not exists"),
	GPORTAL_005("User does not exists"),
	GPORTAL_006("Account Holder Master does not exists"),
	GPORTAL_007("Name is missing"),
	GPORTAL_008("Name already exists"),
	GPORTAL_009("Holder Group Master does not exists"),
	GPORTAL_010("Account Name Master does not exists"),
	GPORTAL_011("Holder Group Master cannot be deleted as it has existing account holder master"),
	GPORTAL_012("Company cannot be deleted as it has existing account name master"),
	GPORTAL_013("Account Holder Master cannot be deleted as it has existing account name master"),
	GPORTAL_014("User Name is missing"),
	GPORTAL_015("Selected role does not exists"),
	GPORTAL_016("Invalid username/password");
	
	private String description;

	private ErrorCodeEnum(String str) {
		this.description = str;
	}

	public String getDescription() {
		return description;
	}
}
