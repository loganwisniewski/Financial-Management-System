package unl.cse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator; 

public class PortfolioReport {
	
	static PortfolioList<Portfolio> portfolioCollection = new PortfolioList<Portfolio>();
	static ArrayList<Asset> assetCollection = new ArrayList<Asset>();
	static ArrayList<Persons> peopleCollection = new ArrayList<Persons>();
	static ArrayList<Asset> portfolioAssets = new ArrayList<Asset>();
	/*
	 * 		MAIN
	 */
	public static void main(String args[]){
		
		makeItHappen();
	}
	
	public static void makeItHappen(){
		
		
		AccountManager m = new AccountManager();
		assetCollection = DataConverter.importAssets(m);
		peopleCollection = DataConverter.importPersons(m);
		
		System.out.println("    Ordered by Name");
		portfolioCollection = DataConverter.importPortfolios(m, new ComparatorOwner());
		m.printPortfolioSummary();
		
		System.out.println("    Ordered by Value");
		portfolioCollection.clear();
		portfolioCollection = DataConverter.importPortfolios(m, new ComparatorTotalValue()); 
		m.printPortfolioSummary();
		
		System.out.println("    Ordered by Manager");
		portfolioCollection.clear();
		portfolioCollection = DataConverter.importPortfolios(m, new ComparatorBroker()); 
		m.printPortfolioSummary();
		//m.printPortfolioDetails();
		
	}
	
	
	
	/*
	 * 		PRINTS PORTFOLIO SUMMARY
	 */
	public void printPortfolioSummary(){
		System.out.println("    Portfolio Summary Report");
		System.out.println("=================================================================================================================================");
		System.out.printf("%-11s%-21s%-21s%-12s%-16s%-20s%-16s%-20s\n", "Portfolio", "Owner", "Manager",
				"Fees", "Commissions", "Weighted Risk", "Return", "Total");
		
		double sumOfFees = 0;
		double sumOfCommission = 0;
		double sumOfAnnualReturn = 0;
		double sumOfValues = 0;
		double annualReturn = 0;
		double totalValue = 0;
		double sumOfTotalValue = 0;
		
		//	LOOP THROUGH 'i' NUMBERS OF PORTFOLIOS
		for(int i = 1; i < portfolioCollection.size(); i++){
			Portfolio p = portfolioCollection.get(i);
			ArrayList<CalculateAsset> cA = new ArrayList<CalculateAsset>();
			
			sumOfValues = 0;
			totalValue = 0;
			annualReturn = 0;
			double weightedOmega = 0;
			double fee = 0;
			double commission = 0;
			String owner = "";
			String manager = "";
			
			// LOOP THROUGH 'k' NUMBER OF ASSETS'
			if(p.getAssetList() != null){
				for(int k = 0; k < p.getAssetList().length; k++){
					CalculateAsset calculateAsset = new CalculateAsset(p.getAssetList()[k], assetCollection);
					cA.add(calculateAsset);
					calculateAsset.calculate();
					CalculateAsset c = cA.get(k);
					
					sumOfValues += c.getTotalValue();
					annualReturn += c.getAnnualReturn();
					totalValue += c.getTotalValue();
					
				}
				
				
				for(int k = 0; k < p.getAssetList().length; k++){
					CalculateAsset calculateAsset = new CalculateAsset(p.getAssetList()[k], assetCollection);
					cA.add(calculateAsset);
					calculateAsset.calculate();
					CalculateAsset c = cA.get(k);
					weightedOmega += c.getOmega() * c.getTotalValue() / sumOfValues;
				}
				
				sumOfTotalValue += totalValue;
				sumOfAnnualReturn += annualReturn;
			
			
				for(int j = 0; j < peopleCollection.size(); j++){
					Persons t = peopleCollection.get(j);
					/*
					 * 		Owner
					 */
					if(p.getOwnerCode().equals(t.getCode())){
						owner = t.getName();
					}
					/*
					 * 		Manager
					 */
					if(p.getManagerCode().equals(t.getCode())){
						manager = t.getName();
						if(t instanceof Junior){
							fee = 50 * p.getAssetList().length;
							commission = .02 * annualReturn;
							sumOfFees += fee;
							sumOfCommission += commission;
						}
						if (t instanceof Expert){
							fee = 10 * p.getAssetList().length;
							commission = .05 * annualReturn;
							sumOfFees += fee;
							sumOfCommission += commission;
						}
					} 				
				}
				
			}
			
			for(int j = 0; j < peopleCollection.size(); j++){
				Persons t = peopleCollection.get(j);
				if(p.getManagerCode().equals(t.getCode())){
					manager = t.getName();
					}
				
				if(p.getOwnerCode().equals(t.getCode())){
					owner = t.getName();
				}
			}
			
			System.out.printf("%-11s%-21s%-21s$%-11.2f$%-15.2f%-20.4f$%-15.2f$%.2f\n", p.getPortfolioCode(), owner,
					manager, fee, commission, weightedOmega, annualReturn, totalValue);
		}
		System.out.printf("%130s\n", "--------------------------------------------------------------------------------------------");
		System.out.printf("%40s%-13s$%-11.2f$%-35.2f$%-15.2f$%.2f\n", "", "Totals", sumOfFees, sumOfCommission, sumOfAnnualReturn, sumOfTotalValue);
		System.out.println();
		System.out.println();
	}
	
	/*
	 * 		PRINTS PORTFOLIO DETAILS
	 */
	public void printPortfolioDetails(){
		System.out.println("   Portfolio Details");
		System.out.println("==================================================================================================================================");
		for(int i = 0; i < portfolioCollection.size(); i++){
			Portfolio p = portfolioCollection.get(i);
			String beneficiary = "none";
			System.out.printf("Portfolio %s\n", p.getPortfolioCode());
			System.out.println("----------------------------------------------------");
			
			/*
			 * 		Loops Through People
			 */
			for(int j = 0; j < peopleCollection.size(); j++){
				Persons t = peopleCollection.get(j);

				/*
				 * 		Owner
				 */
				if(p.getOwnerCode().equals(t.getCode())){
					System.out.printf("Owner: %s\n", t.getName());
				}
				/*
				 * 		Manager
				 */
				if(p.getManagerCode().equals(t.getCode())){
					System.out.printf("Manager: %s\n", t.getName());
				}
				/*
				 * 		Beneficiary
				 */
				if(p.getBeneficiaryCode().equals(t.getCode())){
					beneficiary = t.getName();
				} 
			}
			System.out.printf("Beneficiary: %s\n", beneficiary);
			/*
			 * 		Assets
			 */
			System.out.println("Assets");
			System.out.printf("%-11s%-40s%-13s%-14s%-24s%s\n", "Code","Asset","Return Rate","Risk (omega)", "Annual Return","Value");
			
			ArrayList<CalculateAsset> cA = new ArrayList<CalculateAsset>();
			
			double sumOfValues = 0;
			double sumOfAnnualReturn = 0;
			double sumOfTotalValue = 0;
			double weightedOmega = 0;
			if(p.getAssetList() != null){
				for(int k = 0; k < p.getAssetList().length; k++){
					CalculateAsset calculateAsset = new CalculateAsset(p.getAssetList()[k], assetCollection);
					cA.add(calculateAsset);
					calculateAsset.calculate();
					CalculateAsset c = cA.get(k);
					System.out.printf("%-11s%-40s%-6.2f%-7s%-14.2f%-24.2f%.2f\n", c.getGivenCode(), c.getPortfolioName(), c.getReturnRate(), "%",
							c.getOmega(), c.getAnnualReturn(), c.getTotalValue());
					sumOfValues += c.getTotalValue();
					sumOfAnnualReturn += c.getAnnualReturn();
					sumOfTotalValue += c.getTotalValue();
				}
			
			
				for(int k = 0; k < p.getAssetList().length; k++){
					CalculateAsset calculateAsset = new CalculateAsset(p.getAssetList()[k], assetCollection);
					cA.add(calculateAsset);
					calculateAsset.calculate();
					CalculateAsset c = cA.get(k);
					weightedOmega += c.getOmega() * c.getTotalValue() / sumOfValues;
				}
			}
			
			System.out.printf("%120s\n", "--------------------------------------------------------------------------");
			System.out.printf("%57s%13.4f%8s%-24.2f%.2f\n", "Totals", weightedOmega,"", sumOfAnnualReturn, sumOfTotalValue);
			
			System.out.println();
		}
	}
}
