package unl.cse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AccountManager {

	/*
	 * 
	 * 			ASSET METHODS
	 * 
	 * 			1. addAsset
	 * 			2. addDepositAccount
	 * 			3. addStock
	 * 			4. addPrivateInvestment
	 * 
	 * 
	 */
	public ArrayList<Asset> assetCollection = new ArrayList<Asset>();
	
	/*
	 *
	 *		ADD ASSET METHOD
	 *
	 */

	public ArrayList<Asset> addAsset(ArrayList<String> assetVars){
		
    	if(assetVars.size() == 4){
    		addDepositAccount(assetVars.get(0), assetVars.get(1), assetVars.get(2), Double.parseDouble(assetVars.get(3)));
    	}
    
    	if(assetVars.size() == 8){
    		addStock(assetVars.get(0), assetVars.get(1), assetVars.get(2), Double.parseDouble(assetVars.get(3)),
    				Double.parseDouble(assetVars.get(4)), Double.parseDouble(assetVars.get(5)), assetVars.get(6), Double.parseDouble(assetVars.get(7)));
    	}
    	
    	if(assetVars.size() == 7){
    		addPrivateInvestment(assetVars.get(0), assetVars.get(1), assetVars.get(2), Double.parseDouble(assetVars.get(3)), Double.parseDouble(assetVars.get(4)),
    				Double.parseDouble(assetVars.get(5)), Double.parseDouble(assetVars.get(6)));
    	}
    	
    	return assetCollection;
	}
	
	
	/*
	 *
	 *		ADD DEPOSIT ACCOUNT METHOD
	 *
	 */
	
	public void addDepositAccount(String code, String type, String label, double apr){
		
		DepositAccount d = new DepositAccount(code, type, label, apr);
		assetCollection.add(d);
		
	}
	
	/*
	 *
	 *		ADD STOCK METHOD
	 *
	 */
	
	public void addStock(String code, String type, String label, double quarterlyDividend,
			double baseRateofReturn, double omegaMeasure, String stockSymbol, double sharePrice){
		
		Stock s = new Stock(code, type, label, quarterlyDividend, baseRateofReturn, omegaMeasure, stockSymbol, sharePrice);
		assetCollection.add(s);
		
	}
	
	
	/*
	 *
	 *		ADD PRIVATE INVESTMENT METHOD
	 *
	 */
	
	public void addPrivateInvestment(String code, String type, String label, double quarterlyDividend,
			double baseRateofReturn, double omegaMeasure, double totalValue){
		
		PrivateInvestments p = new PrivateInvestments(code, type, label, quarterlyDividend, baseRateofReturn, omegaMeasure, totalValue);
		assetCollection.add(p);
		
	}
	
	public void assetsToXML(ArrayList<Asset> assetCollection){
		Convert convert = new Convert();
		convert.assetsToXML(assetCollection);
	}
	
	
	/*
	 * 
	 * 			PERSONS METHODS
	 * 
	 * 			1. addPerson
	 * 			2. addNonBroker
	 * 			3. addBroker
	 * 			4. addExpert //Broker
	 * 			5. addJunior //Broker
	 * 
	 * 
	 */
	
	public ArrayList<Persons> peopleCollection = new ArrayList<Persons>();
	
	/*
	 * 
	 * 		ADD PERSON
	 * 
	 */
	public ArrayList<Persons> addPerson(ArrayList<String> personVars){
		
		if(personVars.size() < 5){
			personVars.add(""); // ADDS A BLANK WHERE AN EMAIL SHOULD BE
		}
		
		String fullName = personVars.get(3) + ", " +personVars.get(2);
		
		if(!personVars.get(1).equals("null,null")){
			addBroker(personVars.get(0), personVars.get(1), fullName, "", personVars.get(4));
		} else{
			addNonBroker(personVars.get(0), fullName, "", personVars.get(4));
		}
		
		return peopleCollection;
		
	}
	
	/*
	 * 
	 * 		ADD NON-BROKER
	 * 
	 */
	private void addNonBroker(String code, String name, String address, String email){
		
		NonBroker nb = new NonBroker(code, "", name, address, email);
		peopleCollection.add(nb);
	}
	
	/*
	 * 
	 * 		ADD BROKER
	 * 
	 */
	private void addBroker(String code, String brokerData, String name, String address, String email){
		
		if(brokerData.substring(0,1).equals("E")){
			addExpert(code, brokerData, name, address, email);
		}
		
		if(brokerData.substring(0,1).equals("J")){
			addJunior(code, brokerData, name, address, email);
		} 
		
		
	}
	
	/*
	 * 
	 * 		ADD EXPERT BROKER
	 * 
	 */
	private void addExpert(String code, String brokerData, String name, String address, String email){
		Expert e = new Expert(code, brokerData, name, address, email);
		peopleCollection.add(e);
	}
	/*
	 * 
	 * 		ADD JUNIOR BROKER
	 * 
	 */
	private void addJunior(String code, String brokerData, String name, String address, String email){
		Junior j = new Junior(code, brokerData, name, address, email);
		peopleCollection.add(j);
	}
	/*
	 * 
	 * 		CONVERT PERSONS TO XML
	 * 
	 */
	public void personsToXML(ArrayList<Persons> peopleCollection){
		Convert convert = new Convert();
		convert.namesToXML(peopleCollection);
	}
	
	
	/*
	 * 
	 * 		PORTFOLIO METHODS
	 * 
	 * 		1. addPortfolio
	 * 		2. printPortfolioDetails
	 * 		3. printPortfolioSummary
	 * 
	 * 
	 */
	
	public PortfolioList<Portfolio> portfolioCollection = new PortfolioList<Portfolio>();
	
	/*
	 * 
	 * 		ADD PORTFOLIO
	 * 
	 */
	public PortfolioList<Portfolio> addPortfolio(ArrayList<String> portfolioVars, Comparator comp){
			
		Portfolio p = new Portfolio(portfolioVars);
		//portfolioCollection.add(p, new ComparatorOwner());
		//portfolioCollection.add(p, new ComparatorTotalValue());
		portfolioCollection.add(p, comp);

		
		return portfolioCollection;
	}
	/*
	 * 
	 * 		PRINT PORTFOLIO DETAILS
	 * 
	 */
	public void printPortfolioDetails(){
		PortfolioReport pr = new PortfolioReport();
		pr.printPortfolioDetails();
		
	}
	
	/*
	 * 
	 * 		PRINT PORTFOLIO SUMMARY
	 * 
	 */
	public void printPortfolioSummary(){
		PortfolioReport pr = new PortfolioReport();
		pr.printPortfolioSummary();
		
	}
	
}
