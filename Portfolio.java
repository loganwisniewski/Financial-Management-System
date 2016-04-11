package unl.cse;

import java.util.ArrayList;
import java.util.Comparator;

public class Portfolio {

	private String portfolioCode;
	private String ownerCode;
	private String managerCode;
	private String beneficiaryCode = "";//Default empty
	private String[] assetList = null;
	
	
	public Portfolio(ArrayList<String> input) {
		this.portfolioCode = input.get(0);
		this.ownerCode = input.get(1);
		this.managerCode = input.get(2);
		try{
			this.beneficiaryCode = input.get(3);
		} catch (Exception e) {
			
		}
		try{
			this.assetList = input.get(4).split(",");
		} catch (Exception e) {
			
		}
	}
	
	public Portfolio() {
	
	}


	public String getPortfolioCode() {
		return portfolioCode;
	}


	public String getOwnerCode() {
		return ownerCode;
	}


	public String getManagerCode() {
		return managerCode;
	}


	public String getBeneficiaryCode() {
		return beneficiaryCode;
	}


	public String[] getAssetList() {
		return assetList;
	}
	
	public String getOwnerName(){
		String name = "";
		for(Persons i : DataConverter.peopleCollection){
			if(this.ownerCode.equals(i.getCode())){
				name = i.getName();
			}
		}
		return name;
	}
	
	public String getManagerName(){
		String name = "";
		for(Persons i : DataConverter.peopleCollection){
			if(this.managerCode.equals(i.getCode())){
				name = i.getName();
			}
		}
		return name;
	}
	
	public String getBrokerType(){
		String brokerType = "";
		for(Persons i : DataConverter.peopleCollection){
			if(this.managerCode.equals(i.getCode())){
				brokerType = i.getBrokerData().substring(0,1);
			}
		}
		return brokerType;
	}
	
	
	public double getTotalValue(){
		ArrayList<CalculateAsset> cA = new ArrayList<CalculateAsset>();
		double totalValue = 0;
		double sumOfValues = 0;
		double annualReturn = 0;
		Portfolio p = this;
		if(p.getAssetList() != null){
			for(int k = 0; k < p.getAssetList().length; k++){
				CalculateAsset calculateAsset = new CalculateAsset(p.getAssetList()[k], PortfolioReport.assetCollection);
				cA.add(calculateAsset);
				calculateAsset.calculate();
				CalculateAsset c = cA.get(k);
				
				sumOfValues += c.getTotalValue();
				annualReturn += c.getAnnualReturn();
				totalValue += c.getTotalValue();
				
			}
		}
		
		return totalValue;
	}

}
