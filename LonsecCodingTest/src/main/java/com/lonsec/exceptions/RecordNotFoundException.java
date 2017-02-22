package com.lonsec.exceptions;

public class RecordNotFoundException extends BusinessException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
		
	public RecordNotFoundException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public String getCode()
	{
		return this.code;
	}

}
