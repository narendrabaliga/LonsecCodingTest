package com.lonsec.domain;

public class FinanceInstrument {
	
	private String code;
	
	private String name;
	
	private String bmCode;
	
	private InstrumentReturns returns;
	
	
	/** constructor 
	 * Initialize a Bench Mark
	 */
	public FinanceInstrument(String code,  String name) {
		this.code = code;
		this.name = name;
	}
	
	/** constructor
	 * initialize a Fund
	 */
	public FinanceInstrument(String code, String name , String bmCode) {
		this.code = code;
		this.name = name;
		this.bmCode = bmCode;
	}
	
	public InstrumentReturns getReturns() {
		return returns;
	}
	
	public void setReturns(InstrumentReturns returns) {
		this.returns = returns;
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getBmCode() {
		return bmCode;
	}
	
	public String toString() {
		return this.code +" "+this.name+" "+this.bmCode;
	}

	
}
