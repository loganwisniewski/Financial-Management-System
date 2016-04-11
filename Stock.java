package unl.cse;

import java.awt.SystemColor;

import unl.cse.Asset;

public class Stock extends Asset{

	private double quarterlyDividend;
	private double baseRateofReturn;
	private double omegaMeasure;
	private String stockSymbol;
	private double sharePrice;
	private String code;
	private String label;
	private String type;
	
	private double totalValue;
	private double annualReturn;
	
	

	public Stock(String code, String type, String label,
			double quarterlyDividend, double baseRateofReturn,
			double omegaMeasure, String stockSymbol, double sharePrice) {
		super(code, label, type);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateofReturn = baseRateofReturn;
		this.omegaMeasure = omegaMeasure;
		this.stockSymbol = stockSymbol;
		this.sharePrice = sharePrice;
		this.code = code;
		this.label = label;
		this.type = type;
	}
	

	public String getCode(){
		return this.code;
	}
	public double getApr(){
		return 0.0;
	}
	public String getLabel(){
		return this.label;
	}
	public String getType(){
		return this.type;
	}
	public double getQuarterlyDividend(){
		return this.quarterlyDividend;
	}
	public double getBaseRateofReturn(){
		return this.baseRateofReturn;
	}
	public double getOmegaMeasure(){
		return this.omegaMeasure;
	}
	public double getTotalValue(){
		return 0;
	}
	public String getStockSymbol(){
		return this.stockSymbol;
	}
	public double getSharePrice(){
		return this.sharePrice;
	}
	public double getAnnualReturn() {
		return annualReturn;
	}

}
