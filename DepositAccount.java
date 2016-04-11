package unl.cse;

import unl.cse.Asset;

import java.io.*;

public class DepositAccount extends Asset {
	
	private double apr;
	private String code;
	private String label;
	private String type;
	
	private double totalValue; 
	private double baseRateOfReturn;
	private double annualReturn;
	private double omegaMeasure = 0;
	
	public DepositAccount(String code, String type, String label, double apr) {
		super(code, label, type);
		this.apr = apr;
		this.code = code;
		this.label = label;
		this.type = type;
	}
	
	public String getCode(){
		return this.code;
	}
	public double getApr(){
		return this.apr;
	}
	public String getLabel(){
		return this.label;
	}
	public String getType(){
		return this.type;
	}
	public double getQuarterlyDividend(){
		return 0;
	}
	public double getBaseRateofReturn(){
		return 0;
	}
	public double getOmegaMeasure(){
		return 0;
	}
	public double getTotalValue(){
		return 0;
	}
	public String getStockSymbol(){
		return "";
	}
	public double getSharePrice(){
		return 0;
	}
	public double getBaseRateOfReturn() {
		return baseRateOfReturn;
	}
	public double getAnnualReturn() {
		return annualReturn;
	}
	
}
