package com.lonsec.report.entities;

import java.time.LocalDate;

public class FundReport {
	
	String code;
	String fundName;
	LocalDate date;
	float excess;
	String performance;
	double returns;
	int rank;
	
	public FundReport() {
		
	}
	
	public FundReport(String code, String fundName, LocalDate date, float excess, String performance, float returns, int rank) {
		this.code = code;
		this.fundName = fundName;
		this.date = date;
		this.excess = excess;
		this.performance = performance;
		this.returns = returns;
		this.rank = rank;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public float getExcess() {
		return excess;
	}
	public void setExcess(float excess) {
		this.excess = excess;
	}
	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	public double getReturns() {
		return returns;
	}
	public void setReturns(double returns) {
		this.returns = returns;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}

	public String toString() {
		return this.code+" "+this.fundName+" "+this.date+" "+this.excess+" "+this.performance+" "+this.returns+" "+this.rank;
	}
}
