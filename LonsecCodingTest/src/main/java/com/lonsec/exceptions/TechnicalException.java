package com.lonsec.exceptions;

public class TechnicalException extends RuntimeException {
	
	private String code;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TechnicalException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public String getCode()
	{
		return this.code;
	}

}
