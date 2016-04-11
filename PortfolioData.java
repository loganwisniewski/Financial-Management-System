package unl.cse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class PortfolioData {

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		
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
		

		String query1 = "DELETE FROM Email";
		String query2 = "DELETE FROM Address";
		String query3 = "DELETE FROM Person";
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.executeUpdate();
			ps2 = conn.prepareStatement(query2);
			ps2.executeUpdate();
			ps3 = conn.prepareStatement(query3);
			ps3.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(ps1 != null && !ps1.isClosed()) {
					ps1.close();
				}
				if(ps2 != null && !ps2.isClosed()) {
					ps2.close();
				}
				if(ps3 != null && !ps3.isClosed()) {
					ps3.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
	
	
	/**
	 * Removes the person record from the database corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 */
	public static void removePerson(String personCode) {
		
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

		String query1 = "DELETE FROM Email WHERE PersonID IN " +
				"(SELECT PersonID FROM Person WHERE PersonCode = ?);";
		String query2 = "DELETE  FROM Address WHERE PersonID IN" +
				" (SELECT  PersonID FROM    Person WHERE   PersonCode = ?);";
		String query3 = "DELETE FROM Person WHERE PersonCode = ?";
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, personCode);
			ps1.executeUpdate();
			
			ps2 = conn.prepareStatement(query2);
			ps2.setString(1, personCode);
			ps2.executeUpdate();
			
			ps3 = conn.prepareStatement(query3);
			ps3.setString(1, personCode);
			ps3.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(ps1 != null && !ps1.isClosed()) {
					ps1.close();
				}
				if(ps2 != null && !ps2.isClosed()) {
					ps2.close();
				}
				if(ps3 != null && !ps3.isClosed()) {
					ps3.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or 
	 * <code>null</code> if the person is not a broker.
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param brokerType
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country, String brokerType, String secBrokerId) {
		
		String brokerCode = brokerType + "," + secBrokerId;
		
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

		String query1 = "INSERT INTO Person(PersonCode, BrokerCode, FirstName, LastName) VALUES(?, ?, ?, ?)";
		String query2 = "INSERT INTO Address(PersonID, Street, City, State, Zip, Country) " +
				"VALUES ((SELECT PersonID FROM Person WHERE PersonCode = ?), ?, ?, ?, ?, ?)";
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, personCode);
			ps1.setString(2, brokerCode);
			ps1.setString(3, firstName);
			ps1.setString(4, lastName);
			ps1.executeUpdate();
			
			ps2 = conn.prepareStatement(query2);
			ps2.setString(1, personCode);
			ps2.setString(2, street);
			ps2.setString(3, city);
			ps2.setString(4, state);
			ps2.setString(5, zip);
			ps2.setString(6, country);
			ps2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(ps1 != null && !ps1.isClosed()) {
					ps1.close();
				}
				if(ps2 != null && !ps2.isClosed()) {
					ps2.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		
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

		String query1 = "INSERT INTO Email(PersonID, EmailAddress) " +
				"VALUES ((SELECT PersonID FROM Person WHERE PersonCode = ?), ?)";
		
		PreparedStatement ps1 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, personCode);
			ps1.setString(2, email);
			ps1.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
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
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Removes all asset records from the database
	 */
	public static void removeAllAssets() {
		
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

		String query1 = "DELETE FROM PortfolioAsset";
		String query2 = "DELETE FROM Asset";
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.executeUpdate();
			ps2 = conn.prepareStatement(query2);
			ps2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(ps1 != null && !ps1.isClosed()) {
					ps1.close();
				}
				if(ps2 != null && !ps2.isClosed()) {
					ps2.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Removes the asset record from the database corresponding to the
	 * provided <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {
		
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

		String query1 = "DELETE FROM PortfolioAsset WHERE AssetID IN " +
				"(SELECT AssetID FROM Asset WHERE AssetCode = ?)";
		String query2 = "DELETE FROM Asset WHERE AssetCode = ?";
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, assetCode);
			ps1.executeUpdate();
			ps2 = conn.prepareStatement(query2);
			ps2.setString(1, assetCode);
			ps2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(ps1 != null && !ps1.isClosed()) {
					ps1.close();
				}
				if(ps2 != null && !ps2.isClosed()) {
					ps2.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Adds a deposit account asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {
		
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

		String query1 = "INSERT INTO Asset (AssetCode, AssetType, Label, APR) " +
				"VALUES (?, 'D', ?, ?);";
		
		PreparedStatement ps1 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, assetCode);
			ps1.setString(2, label);
			ps1.setDouble(3, apr);
			ps1.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
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
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a private investment asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param omega
	 * @param totalValue
	 */
	public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double omega, Double totalValue) {
		
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

		String query1 = "INSERT INTO Asset (AssetCode, AssetType, Label, QuarterlyDividend, BaseRateOfReturn, OmegaMeasure, TotalValue) " +
				"VALUES (?, 'P', ?, ?, ?, ?, ?);";
		
		PreparedStatement ps1 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, assetCode);
			ps1.setString(2, label);
			ps1.setDouble(3, quarterlyDividend);
			ps1.setDouble(4, baseRateOfReturn);
			ps1.setDouble(5, omega);
			ps1.setDouble(6, totalValue);
			ps1.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
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
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Adds a stock asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param omega
	 * @param stockSymbol
	 * @param sharePrice
	 */
	public static void addStock(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double omega, String stockSymbol, Double sharePrice) {
		
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

		String query1 = "INSERT INTO Asset (AssetCode, AssetType, Label, QuarterlyDividend, BaseRateOfReturn, OmegaMeasure, StockSymbol, SharePrice) " +
				"VALUES (?, 'S', ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement ps1 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, assetCode);
			ps1.setString(2, label);
			ps1.setDouble(3, quarterlyDividend);
			ps1.setDouble(4, baseRateOfReturn);
			ps1.setDouble(5, omega);
			ps1.setString(6, stockSymbol);
			ps1.setDouble(7, sharePrice);
			ps1.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
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
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {
		
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

		String query1 = "DELETE FROM PortfolioAsset";
		String query2 = "DELETE FROM Portfolio";
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.executeUpdate();
			ps2 = conn.prepareStatement(query2);
			ps2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(ps1 != null && !ps1.isClosed()) {
					ps1.close();
				}
				if(ps2 != null && !ps2.isClosed()) {
					ps2.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Removes the portfolio record from the database corresponding to the
	 * provided <code>portfolioCode</code>
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portfolioCode) {
		
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

		String query1 = "DELETE FROM PortfolioAsset WHERE PortfolioID IN " +
				"(SELECT PortfolioID FROM Portfolio WHERE PortfolioCode = ?)";
		String query2 = "DELETE FROM Portfolio WHERE PortfolioCode = ?";
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, portfolioCode);
			ps1.executeUpdate();
			ps2 = conn.prepareStatement(query2);
			ps2.setString(1, portfolioCode);
			ps2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(ps1 != null && !ps1.isClosed()) {
					ps1.close();
				}
				if(ps2 != null && !ps2.isClosed()) {
					ps2.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Adds a portfolio records to the database with the given data.  If the portfolio has no
	 * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
		
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

		String query1 = "INSERT INTO Portfolio (PortfolioCode, OwnerID, ManagerID, BeneficiaryID) " +
				"VALUES (?, (SELECT PersonID FROM Person WHERE PersonCode = ?)," +
				" (SELECT PersonID FROM Person WHERE PersonCode = ?)," +
				" (SELECT PersonID FROM Person WHERE PersonCode = ?));";
		
		PreparedStatement ps1 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, portfolioCode);
			ps1.setString(2, ownerCode);
			ps1.setString(3, managerCode);
			ps1.setString(4, beneficiaryCode);
			ps1.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
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
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Associates the asset record corresponding to <code>assetCode</code> with the 
	 * portfolio corresponding to the provided <code>portfolioCode</code>.  The third 
	 * parameter, <code>value</code> is interpreted as a <i>balance</i>, <i>number of shares</i>
	 * or <i>stake percentage</i> depending on the type of asset the <code>assetCode</code> is
	 * associated with.
	 * @param portfolioCode
	 * @param assetCode
	 * @param value
	 */
	public static void addAsset(String portfolioCode, String assetCode, double givenValue) {
		
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

		String query1 = "INSERT INTO PortfolioAsset (AssetID, PortfolioID, GivenValue) " +
				"VALUES ((SELECT AssetID FROM Asset WHERE AssetCode = ?)," +
				" (SELECT PortfolioID FROM Portfolio WHERE PortfolioCode = ?), ?);";
		
		PreparedStatement ps1 = null;
		
		try {
			ps1 = conn.prepareStatement(query1);
			ps1.setString(1, assetCode);
			ps1.setString(2, portfolioCode);
			ps1.setDouble(3, givenValue);
			ps1.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
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
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	
}
