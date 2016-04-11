package unl.cse;

import unl.cse.Asset;

public class PrivateInvestments extends Asset{
	
	private double quarterlyDividend;
	private double baseRateofReturn;
	private double omegaMeasure;
	private double totalValue;
	private String code;
	private String label;
	private String type;
	
	private double annualReturn;
	
	
	public PrivateInvestments(String code, String type, String label,
			double quarterlyDividend, double baseRateofReturn,
			double omegaMeasure, double totalValue) {
		super(code, label, type);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateofReturn = baseRateofReturn;
		this.omegaMeasure = omegaMeasure;
		this.totalValue = totalValue;
		this.label = label;
		this.type = type;
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
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
		return this.totalValue;
	}
	public String getLabel(){
		return this.label;
	}
	public String getType(){
		return this.type;
	}
	public double getApr(){
		return 0;
	}
	public String getStockSymbol(){
		return "";
	}
	public double getSharePrice(){
		return 0;
	}
	
	public double getAnnualReturn() {
		return annualReturn;
	}
	
}
