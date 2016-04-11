package unl.cse;

import java.util.ArrayList;

public class CalculateAsset {
	
	//ASSET VARIABLES
	private double returnRate;
	private double omega;
	private double annualReturn;
	private double totalValue;
	private double givenValue;
	private String givenCode;
	ArrayList<Asset> assetCollection;
	private String portfolioCode;
	private String portfolioName;
	
	/*
	 * 		Assign Variables
	 */
	public CalculateAsset(String assetCode, ArrayList<Asset> assetCollection){
		this.givenCode = assetCode.split(":")[0];
		this.givenValue = Double.parseDouble(assetCode.split(":")[1]);
		this.assetCollection = assetCollection;
	}
	
	public void resetValues(){
		this.annualReturn = 0;
		this.totalValue = 0;
	}
	
	/*
	 * 		Calcultion of Remaining Variables
	 */
	public void calculate(){
		//loops though assets
		resetValues();
		for(int i = 0; i < assetCollection.size(); i++){
			if(givenCode.equals(assetCollection.get(i).getCode())){
				Asset a = assetCollection.get(i);
				//STOCK
				if(a instanceof Stock){
					calculateStock(a);
					this.portfolioCode = a.getCode();
					this.portfolioName = a.getLabel();
					this.totalValue = (a.getSharePrice()*givenValue);
					this.annualReturn = ( a.getBaseRateofReturn() * totalValue + a.getQuarterlyDividend() * 4 * givenValue * 100)/100;
					this.returnRate = (annualReturn/(a.getSharePrice()*givenValue)*100);
					this.omega = a.getOmegaMeasure() + .10;
					
					
				}
				//PRIVATE INVESTMENTS
				if(a instanceof PrivateInvestments){
					calculatePrivateInvestment(a);
					this.portfolioCode = a.getCode();
					this.portfolioName = a.getLabel();
					this.totalValue = a.getTotalValue();
					this.annualReturn = (a.getBaseRateofReturn() * a.getTotalValue() * (givenValue/100) +  a.getQuarterlyDividend() * 4 * givenValue)/100;
					this.returnRate = (annualReturn/totalValue) * 100;
					this.omega = a.getOmegaMeasure() + .25;
				}
				//DEPOSIT ACCOUNT
				if(a instanceof DepositAccount){
					calculateDepositAccount(a);
					this.portfolioCode = a.getCode();
					this.portfolioName = a.getLabel();
					this.returnRate = ((Math.pow(Math.E, (a.getApr()/100))) - 1) * 100;
					//returnRate = 0;
					this.omega = 0;
					this.annualReturn = givenValue * returnRate / 100;
					this.totalValue = givenValue;
					
				}
				
			}
		}
	}
	
	// STOCK TOTAL VALUE
	public void calculateStock(Asset a){
		totalValue = givenValue * a.getSharePrice();
	}
	
	// PRIVATE INVESTMENT TOTAL VALUE
	public void calculatePrivateInvestment(Asset a){
		totalValue = a.getTotalValue();
	}
	
	// DEPOSIT ACCOUNT TOTAL VALUE
	public void calculateDepositAccount(Asset a){
		totalValue = givenValue;
	}

	
	/*
	 * 
	 * 		GET METHODS
	 * 
	 */
	public double getReturnRate() {
		return returnRate;
	}

	public double getOmega() {
		return omega;
	}

	public double getAnnualReturn() {
		return annualReturn;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public double getGivenValue() {
		return givenValue;
	}

	public String getGivenCode() {
		return givenCode;
	}

	public String getPortfolioCode() {
		return portfolioCode;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	
	
	
	
	
}
