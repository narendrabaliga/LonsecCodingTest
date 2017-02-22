/**
 * 
 */
package com.lonsec.domain;

import java.time.LocalDate;

/**
 * @author naren
 *
 */
public class InstrumentReturns implements Comparable<InstrumentReturns>{
	private String code;
	private LocalDate monthEnd;
	private double returns;
	private int rank;
	
	
	
	
	/*
	 * Constructor to define returns
	 *  
	 */
	public InstrumentReturns(String code, LocalDate date, double returns) {
		this.code = code;
		this.monthEnd = date;
		this.returns = returns;
		this.rank = 0;
		
				
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDate getMonthEnd() {
		return monthEnd;
	}

	public void setMonthEnd(LocalDate monthEnd) {
		this.monthEnd = monthEnd;
	}

	public double getReturns() {
		return returns;
	}

	public void setReturns(float returns) {
		this.returns = returns;
	}
	public String toString() {
		return this.code +" "+this.monthEnd+" "+this.returns;
	}
	
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	
	@Override
	public int compareTo(InstrumentReturns o) {
		// TODO Auto-generated method stub
		if(this.returns > o.returns) return -1;
		if(this.returns < o.returns) return 1;
		return 0;
	}

}
