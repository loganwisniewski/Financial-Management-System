package unl.cse;

import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/*
 * 
 * 
 * 		CONVERTER FOR JAVA TO XML
 * 
 * 
 */



public class Convert{
	
	private String code;
	private String brokerData[];
	private String name[];
	private String address[];
	private String email[];
	
	/*
	 * 
	 *   METHOD TO CONVERT THE NAMES ARRAY TO XML CODE
	 * 
	 */
	
	
	public void namesToXML(ArrayList<Persons> peopleCollection){
		
		Document doc = new Document();
		Element theRoot = new Element("Persons");
		doc.setRootElement(theRoot);
		
		for(int i = 0; i < peopleCollection.size(); i++){
			
			this.code = peopleCollection.get(i).getCode();
			this.brokerData = peopleCollection.get(i).getBrokerData().split(",");
			this.name = peopleCollection.get(i).getName().replaceAll(", ", ",").split(",");
			
			try{
				
				this.address = peopleCollection.get(i).getAddress().replaceAll(", ", ",").split(",");
				
			} catch(NullPointerException e) {
				
			}
			
			this.email = peopleCollection.get(i).getEmail().split(",");
			
			Element person = new Element("Person");
			Element personName = new Element("name");
			Element first = new Element("first");
			first.addContent(new Text(name[1]));
			Element last = new Element("last");
			last.addContent(new Text(name[0]));
			personName.addContent(first);
			personName.addContent(last);
			Element adrs = new Element("address");
			Element street = new Element("street");
			street.addContent(new Text(address[0]));
			Element city = new Element("city");
			city.addContent(new Text(address[1]));
			Element state = new Element("state");
			state.addContent(new Text(address[2]));
			Element zip = new Element("zip");
			if(!address[3].equals("")){
				zip.addContent(new Text(address[3]));
			}
			Element country = new Element("country");
			country.addContent(new Text(address[4]));
			adrs.addContent(street);
			adrs.addContent(city);
			adrs.addContent(state);
			adrs.addContent(zip);
			adrs.addContent(country);
			
			person.addContent(personName);
			person.addContent(adrs);
			
			Element emails = new Element("emails");
			
			try{
				
					for(int p = 0; p < email.length; p++){
						
						Element email = new Element("email");
						email.addContent(new Text(this.email[p]));
						emails.addContent(email);
						
					}
					if(!this.email[0].equals("")){
						person.addContent(emails);
					}
					
				
			}
			catch(NullPointerException npe){
				System.out.println(npe);
			}
		    theRoot.addContent(person);
		    
		    
		    
		}
		
		try{
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileOutputStream(new File("data/Persons.xml")));
			}
			catch (Exception ex){
				ex.printStackTrace();
			}
		
		
	}
	
	/*
	 * 
	 *   METHOD TO CONVERT THE ASSETS ARRAY TO XML CODE
	 * 
	 */
	public void assetsToXML(ArrayList<Asset> assetCollection){
		
		Document doc = new Document();
		Element theRoot = new Element("Assets");
		doc.setRootElement(theRoot);
		
		
		for(int i = 0; i < assetCollection.size(); i++){
			
			if(assetCollection.get(i) instanceof DepositAccount){
				depositAccountToXML(assetCollection.get(i), theRoot);
			} else if(assetCollection.get(i) instanceof Stock){
				stockToXML(assetCollection.get(i), theRoot);
			} else if(assetCollection.get(i) instanceof PrivateInvestments){
				privateInvestmentToXML(assetCollection.get(i), theRoot);
			} else {
				System.out.println("Not Working!");
			}
		
		
		}
		
		
	
		try{
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileOutputStream(new File("data/Assets.xml")));
			}
			catch (Exception ex){
				ex.printStackTrace();
			}
		
	
	}
	
	
	private double apr;
	private String assetCode;
	private String label;
	private String type;
	
	private String quarterlyDividend;
	private String baseRateOfReturn;
	private String omegaMeasure;
	private String totalValue;
	private String stockSymbol;
	private String sharePrice;
	
	
	/*
	 * 
	 *   METHOD TO CONVERT THE DEPOSIT ACCOUNT ARRAY TO XML CODE
	 * 
	 */
	public void depositAccountToXML(Asset assetCollection, Element theRoot){
		
		this.apr = assetCollection.getApr()/100;
		this.assetCode = assetCollection.getCode();
		this.label = assetCollection.getLabel();
		this.type = assetCollection.getType();
		
		Element asset = new Element("asset");
		Element code = new Element("assetCode");
		code.addContent(new Text(this.assetCode));
		Element label = new Element("label");
		label.addContent(new Text(this.label));
		Element type = new Element("type");
		type.addContent(new Text(this.type));
		Element apr = new Element("apr");
		apr.addContent(new Text(Double.toString(this.apr)));
		asset.addContent(code);
		asset.addContent(label);
		asset.addContent(type);
		asset.addContent(apr);
		theRoot.addContent(asset);
	}
	
	/*
	 * 
	 *   METHOD TO CONVERT THE STOCK ARRAY TO XML CODE
	 * 
	 */
	public void stockToXML(Asset assetCollection, Element theRoot){
		
		
		this.assetCode = assetCollection.getCode();
		this.label = assetCollection.getLabel();
		this.type = assetCollection.getType();
		
		this.quarterlyDividend = Double.toString(assetCollection.getQuarterlyDividend());
		this.baseRateOfReturn = Double.toString(assetCollection.getBaseRateofReturn()/100);
		this.omegaMeasure = Double.toString(assetCollection.getOmegaMeasure());
		this.stockSymbol = assetCollection.getStockSymbol();
		this.sharePrice = Double.toString(assetCollection.getSharePrice());
		
		
		Element asset = new Element("asset");
		Element code = new Element("assetCode");
		code.addContent(new Text(this.assetCode));
		Element label = new Element("label");
		label.addContent(new Text(this.label));
		Element type = new Element("type");
		type.addContent(new Text(this.type));
		Element quarterlyDividend = new Element("quarterlyDividend");
		quarterlyDividend.addContent(new Text(this.quarterlyDividend));
		Element baseRateOfReturn = new Element("baseRateOfReturn");
		baseRateOfReturn.addContent(new Text(this.baseRateOfReturn));
		Element omegaMeasure = new Element("omega");
		omegaMeasure.addContent(new Text(this.omegaMeasure));
		Element stockSymbol = new Element("stockSymbol");
		stockSymbol.addContent(new Text(this.stockSymbol));
		Element sharePrice = new Element("sharePrice");
		sharePrice.addContent(new Text(this.sharePrice));
		asset.addContent(code);
		asset.addContent(label);
		asset.addContent(type);
		asset.addContent(quarterlyDividend);
		asset.addContent(baseRateOfReturn);
		asset.addContent(omegaMeasure);
		asset.addContent(stockSymbol);
		asset.addContent(sharePrice);
		
		
		theRoot.addContent(asset);
	}
	
	/*
	 * 
	 *   METHOD TO CONVERT THE PRIVATE INVESTMENTS ARRAY TO XML CODE
	 * 
	 */
	public void privateInvestmentToXML(Asset assetCollection, Element theRoot){
		
		this.assetCode = assetCollection.getCode();
		this.label = assetCollection.getLabel();
		this.type = assetCollection.getType();
		
		//System.out.println(this.a);
		
		this.quarterlyDividend = Double.toString(assetCollection.getQuarterlyDividend());
		this.baseRateOfReturn = Double.toString(assetCollection.getBaseRateofReturn()/100);
		this.omegaMeasure = Double.toString(assetCollection.getOmegaMeasure());
		this.totalValue = Double.toString(assetCollection.getTotalValue());
		
		
		Element asset = new Element("asset");
		Element code = new Element("assetCode");
		code.addContent(new Text(this.assetCode));
		Element label = new Element("label");
		label.addContent(new Text(this.label));
		Element type = new Element("type");
		type.addContent(new Text(this.type));
		Element quarterlyDividend = new Element("quarterlyDividend");
		quarterlyDividend.addContent(new Text(this.quarterlyDividend));
		Element baseRateOfReturn = new Element("baseRateOfReturn");
		baseRateOfReturn.addContent(new Text(this.baseRateOfReturn));
		Element omegaMeasure = new Element("omega");
		omegaMeasure.addContent(new Text(this.omegaMeasure));
		Element totalValue = new Element("totalValue");
		totalValue.addContent(new Text(this.totalValue));
		asset.addContent(code);
		asset.addContent(label);
		asset.addContent(type);
		asset.addContent(quarterlyDividend);
		asset.addContent(baseRateOfReturn);
		asset.addContent(omegaMeasure);
		asset.addContent(totalValue);
		
		
		theRoot.addContent(asset);
	}
	
	
	
}
