package com.lonsec.exceptions;

public class BusinessException extends Exception {

	private String code;
	
	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
		
	}
	
	public BusinessException(String message, String code) {
		super(message);
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
