package unl.cse;
import unl.cse.AccountManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class DataConverter {

	static ArrayList<Asset> assetCollection = new ArrayList<Asset>();
	public static ArrayList<Asset> importAssets(AccountManager m){
		

		// Prepare to read from the file, using a Scanner object
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;
		
		try{
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e){
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		ResultSet rs = null;

		String query = "SELECT * FROM Asset";
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				ArrayList<String> assetVars = new ArrayList<String>();	
				String assetCode = rs.getString("AssetCode");
				String assetType = rs.getString("AssetType");
				String label = rs.getString("Label");
				String APR = Double.toString(rs.getFloat("APR"));
				String quarterlyDividend = Double.toString(rs.getFloat("QuarterlyDividend"));
				String baseRateOfReturn = Double.toString(rs.getFloat("BaseRateOfReturn"));
				String omegaMeasure = Double.toString(rs.getFloat("OmegaMeasure"));
				String totalValue = Double.toString(rs.getFloat("TotalValue"));
				String stockSymbol = rs.getString("StockSymbol");
				String sharePrice = Double.toString(rs.getFloat("SharePrice"));

				
				if(assetType.equals("P")){
					assetVars.add(assetCode);
					assetVars.add(assetType);
					assetVars.add(label);
					assetVars.add(quarterlyDividend);
					assetVars.add(baseRateOfReturn);
					assetVars.add(omegaMeasure);
					assetVars.add(totalValue);
					
				} else if (assetType.equals("S")){
					assetVars.add(assetCode);
					assetVars.add(assetType);
					assetVars.add(label);
					assetVars.add(quarterlyDividend);
					assetVars.add(baseRateOfReturn);
					assetVars.add(omegaMeasure);
					assetVars.add(stockSymbol);
					assetVars.add(sharePrice);					
				} else if (assetType.equals("D")) {
					assetVars.add(assetCode);
					assetVars.add(assetType);
					assetVars.add(label);
					assetVars.add(APR);
				}
				
				assetCollection = m.addAsset(assetVars);
				
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(rs != null && !rs.isClosed()) {
					rs.close();
				}
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
				
		
		return assetCollection;
		
	}
	
	static ArrayList<Persons> peopleCollection = new ArrayList<Persons>();
	public static ArrayList<Persons> importPersons(AccountManager m){
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;
		
		try{
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e){
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		ResultSet rs1 = null;
		ResultSet rs2 = null;

		String query1 = "SELECT * FROM Person";
		String query2 = "SELECT * FROM Email AS e JOIN Person AS p ON e.PersonID = p.PersonID WHERE PersonCode = ?";
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			rs1 = ps1.executeQuery();
			while(rs1.next()) {
				ArrayList<String> personVars = new ArrayList<String>();	
				String personCode = rs1.getString("PersonCode");
				String brokerCode = rs1.getString("BrokerCode");
				String firstName = rs1.getString("FirstName");
				String lastName = rs1.getString("LastName");
				
				ps2 = conn.prepareStatement(query2);
				ps2.setString(1, personCode);
				rs2 = ps2.executeQuery();
				
				String email = null;
				while(rs2.next()){
					if(email == null){
						email = rs2.getString("EmailAddress");
					} else {
						email = email + "," + rs2.getString("EmailAddress");
					}
					
				}
				
				if(brokerCode == null){
					personVars.add(personCode);
					personVars.add("");
					personVars.add(firstName);
					personVars.add(lastName);
					personVars.add(email);
				} 
				if(brokerCode != null){
					personVars.add(personCode);
					personVars.add(brokerCode);
					personVars.add(firstName);
					personVars.add(lastName);
					personVars.add(email);
				}
					
				peopleCollection = m.addPerson(personVars);
				
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(rs1 != null && !rs1.isClosed()) {
					rs1.close();
				}
				if(ps1 != null && !ps1.isClosed()) {
					ps1.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(rs1 != null && !rs1.isClosed())
				rs1.close();
			if(ps1 != null && !ps1.isClosed())
				ps1.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return peopleCollection;
		
		
	}
	
	static PortfolioList<Portfolio> portfolioCollection = new PortfolioList<Portfolio>();
	public static PortfolioList<Portfolio> importPortfolios(AccountManager m, Comparator comp){
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;
		
		try{
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e){
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;

		String query1 = "SELECT PortfolioCode FROM Portfolio";
		String query2 = "SELECT a.AssetCode, pa.GivenValue, p.PortfolioID FROM Asset AS a" +
				" JOIN PortfolioAsset AS pa ON a.AssetID = pa.AssetID" +
				" JOIN Portfolio as p ON pa.PortfolioID = p.PortfolioID" +
				" WHERE PortfolioCode = ?";
		String query3 = "SELECT pers.PersonCode FROM Person as pers " +
				"JOIN Portfolio AS p ON p.OwnerID = pers.PersonID WHERE p.PortfolioCode = ?";
		String query4 = "SELECT pers.PersonCode FROM Person as pers " +
				"JOIN Portfolio AS p ON p.ManagerID = pers.PersonID WHERE p.PortfolioCode = ?";
		String query5 = "SELECT pers.PersonCode FROM Person as pers " +
				"JOIN Portfolio AS p ON p.BeneficiaryID = pers.PersonID WHERE p.PortfolioCode = ?";
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			rs1 = ps1.executeQuery();
			while(rs1.next()) {
			//for(int i = 0; i < 14; i++){
				//rs1.next();
				ArrayList<String> portfolioVars = new ArrayList<String>();	
				String portfolioCode = rs1.getString("PortfolioCode");
				
				ps2 = conn.prepareStatement(query2);
				ps2.setString(1, portfolioCode);
				rs2 = ps2.executeQuery();
				
				String asset = null;
				while(rs2.next()){
					if(asset == null){
						asset = rs2.getString("AssetCode") + ":" + rs2.getString("GivenValue");
					} else {
						asset = asset + "," + rs2.getString("AssetCode") + ":" + rs2.getString("GivenValue");
					}
					
				}
				
				
				ps3 = conn.prepareStatement(query3);
				ps3.setString(1, portfolioCode);
				rs3 = ps3.executeQuery();
				
				String ownerCode = "";
				if(rs3.next()){
					ownerCode = rs3.getString("PersonCode");
				}
				
				ps4 = conn.prepareStatement(query4);
				ps4.setString(1, portfolioCode);
				rs4 = ps4.executeQuery();
				
				String managerCode = "";
				if(rs4.next()){
					managerCode = rs4.getString("PersonCode");
				}
				
				ps5 = conn.prepareStatement(query5);
				ps5.setString(1, portfolioCode);
				rs5 = ps5.executeQuery();
				
				String beneficiaryCode = "";
				if(rs5.next()){
					beneficiaryCode = rs5.getString("PersonCode");
				}
				
				
				
				
				portfolioVars.add(portfolioCode);
				portfolioVars.add(ownerCode);
				portfolioVars.add(managerCode);
				portfolioVars.add(beneficiaryCode);
				portfolioVars.add(asset);
				
				portfolioCollection = m.addPortfolio(portfolioVars, comp);
				
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(rs1 != null && !rs1.isClosed()) {
					rs1.close();
				}
				if(ps1 != null && !ps1.isClosed()) {
					ps1.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(rs1 != null && !rs1.isClosed())
				rs1.close();
			if(ps1 != null && !ps1.isClosed())
				ps1.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return portfolioCollection;
	}
	
	
	
	
}
