package com.lonsec.exceptions;

public class MultipleRecordsFound extends BusinessException{

	private static final long serialVersionUID = 1L;
	private String code;
		
	public MultipleRecordsFound(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public String getCode()
	{
		return this.code;
	}

}
