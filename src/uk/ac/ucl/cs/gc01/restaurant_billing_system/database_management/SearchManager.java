package uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * 
 * <p>The SearchManager class connects to the database via its constructor. It provides methods that
 * search information from the entire database.</p>
 * 
 * <b>It implements the following methods:</b>
 * <ul>
 * 		<li><b>orderIdSearch(int)</b> - retrieves all the relevant information for the specified orderId and stores
 *  it in an ArrayList of Strings.</li>
 * 		<li><b>tableNumSearch(int)</b> - retrieves all the relevant information for the specified tableNumber and stores
 *  it in an ArrayList of Strings.</li>
 * 		<li><b>foodItemDescriptionSearch(String)</b> - retrieves all the relevant information for the specified foodItemDescription and stores
 *  it in an ArrayList of Strings.</li>
 * 		<li><b>empIdSearch(int)</b> -retrieves all the relevant information for the specified empId and stores
 *  it in an ArrayList of Strings.</li>
 * </ul>
 * 
 * <b>References:</b>
 * <ul>
 * <li>https://www.youtube.com/watch?v=_eCn4pLw350&#38;list=PLS1QulWo1RIaUGP446_pWLgTZPiFizEMq&#38;index=33;</li>
 * <li>http://www.sqlitetutorial.net/sqlite-java/delete/</li>
 * <li> http://www.sqlitetutorial.net/sqlite-java/</li>
 *</ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class SearchManager {

	private Connection connection;
	
	/**
	 * <b>Constructor:</b>
	 * <p>Connects to the database via the Connector static method from the SqliteConnection class.</p>
	 */
	public SearchManager() {
		
		connection = SqliteConnection.Connector();
		
		//handling null if Exception throws
		if(connection == null) 
			System.exit(1);
		
		
	}
	
	/**
	 * <p>Queries the database for all the relevant data for the
	 * specified orderId by creating a joint table between the Order_Items and Order_Details
	 * tables of the database. It returns an ArrayList of Strings contaning the matching
	 * rows separated by a comma.</p>
	 * 
	 * <p><b>SQL query:</b>SELECT O.orderId, tableNumber, Fooditem_description, COUNT(Fooditem_description) as quantity, special_requests, comments, orderTime, orderDate, empID FROM Order_Items as O JOIN Order_Details as F ON O.orderId=F.orderId where O.orderId = ?  GROUP BY O.orderId, O.Fooditem_description</p>
	 * 
	 * @param orderId - specified orderId.
	 * @return  ArrayList&#60;String&#62; - contains all relevant data for the specified orderId.
	 * @throws SQLException - SQL handling exception.
	 */
	public ArrayList<String> orderIdSearch(int orderId) throws SQLException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<String> results = new ArrayList<String>();
		try {
			String query ="SELECT O.orderId, tableNumber, Fooditem_description, COUNT(Fooditem_description) as quantity, special_requests, comments, orderTime, orderDate, empID FROM Order_Items as O JOIN Order_Details as F ON O.orderId=F.orderId where O.orderId = ?  GROUP BY O.orderId, O.Fooditem_description";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, orderId);
			
			resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next()){
				
			results.add(Integer.toString(resultSet.getInt(1)));
			results.add(Integer.toString(resultSet.getInt(2)));
			results.add(resultSet.getString(3));
			results.add(Integer.toString(resultSet.getInt(4)));
			results.add(resultSet.getString(5));
			results.add(resultSet.getString(6));
			results.add(resultSet.getString(7));
			results.add(resultSet.getString(8));
			results.add(Integer.toString(resultSet.getInt(9)));
			
			results.add(",");
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			preparedStatement.close();
		}
		
		return results;
	}
	
	/**
	 * <p>Queries the database for all the relevant data for the
	 * specified orderId by creating a joint table between the Order_Items and Order_Details
	 * tables of the database. It returns an ArrayList of Strings contaning the matching
	 * rows separated by a comma.</p>
	 * 
	 * <p><b>SQL query:</b>SELECT O.orderId, tableNumber, Fooditem_description, COUNT(Fooditem_description) as quantity, special_requests, comments, orderTime, orderDate, empID FROM Order_Items as O JOIN Order_Details as F ON O.orderId=F.orderId where F.tableNumber = ? GROUP BY O.orderId, O.Fooditem_description</p>
	 * 
	 * @param tableNum - the specified table number.
	 * @return ArrayList&#60;String&#62; - contains all relevant data for the specified tableNumber.
	 * @throws SQLException - SQL handling exception.
	 */
	public ArrayList<String> tableNumSearch(int tableNum) throws SQLException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<String> results = new ArrayList<String>();
		try {
			String query ="SELECT O.orderId, tableNumber, Fooditem_description, COUNT(Fooditem_description) as quantity, special_requests, comments, orderTime, orderDate, empID FROM Order_Items as O JOIN Order_Details as F ON O.orderId=F.orderId where F.tableNumber = ? GROUP BY O.orderId, O.Fooditem_description";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, tableNum);
			
			resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next()){
				
				results.add(Integer.toString(resultSet.getInt(1)));
				results.add(Integer.toString(resultSet.getInt(2)));
				results.add(resultSet.getString(3));
				results.add(Integer.toString(resultSet.getInt(4)));
				results.add(resultSet.getString(5));
				results.add(resultSet.getString(6));
				results.add(resultSet.getString(7));
				results.add(resultSet.getString(8));
				results.add(Integer.toString(resultSet.getInt(9)));

				results.add(",");
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			preparedStatement.close();
		}
		
		return results;
	}


	/**
	 * <p>The foodItemDescriptionSearch method queries the database for all the relevant data for the
	 * specified foodItemDescription by creating a joint table between the Order_Items and Order_Details
	 * tables of the database. It returns an ArrayList of Strings contaning the matching
	 * rows separated by a comma.</p>
	 * 
	 * <p><b>SQL query:</b>SELECT O.orderId, tableNumber, Fooditem_description, COUNT(Fooditem_description) as quantity, special_requests, comments, orderTime, orderDate, empID FROM Order_Items as O JOIN Order_Details as F ON O.orderId=F.orderId where Fooditem_description = ? GROUP BY O.orderId, O.Fooditem_description</p>
	 * 
	 * @param foodItemDescription - the specified foodItemDescription.
	 * @return ArrayList&#60;String&#62; - contains all relevant data for the specified foodItemDescription.
	 * @throws SQLException - SQL handling exception.
	 */
	public ArrayList<String> foodItemDescriptionSearch(String foodItemDescription) throws SQLException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<String> results = new ArrayList<String>();
		try {
			String query ="SELECT O.orderId, tableNumber, Fooditem_description, COUNT(Fooditem_description) as quantity, special_requests, comments, orderTime, orderDate, empID FROM Order_Items as O JOIN Order_Details as F ON O.orderId=F.orderId where Fooditem_description = ? GROUP BY O.orderId, O.Fooditem_description";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, foodItemDescription);
			
			resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next()){
				
				results.add(Integer.toString(resultSet.getInt(1)));
				results.add(Integer.toString(resultSet.getInt(2)));
				results.add(resultSet.getString(3));
				results.add(Integer.toString(resultSet.getInt(4)));
				results.add(resultSet.getString(5));
				results.add(resultSet.getString(6));
				results.add(resultSet.getString(7));
				results.add(resultSet.getString(8));
				results.add(Integer.toString(resultSet.getInt(9)));

				results.add(",");
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			preparedStatement.close();
		}
		
		return results;
	}
	
	/**
	 * <p>Queries the database for all the relevant data for the
	 * specified empId by creating a joint table between the Order_Items and Order_Details
	 * tables of the database. It returns an ArrayList of Strings contaning the matching
	 * rows separated by a comma.</p>
	 * 
	 * <p><b>SQL query:</b>SELECT O.orderId, tableNumber, Fooditem_description, COUNT(Fooditem_description) as quantity, special_requests, comments, orderTime, orderDate, empID FROM Order_Items as O JOIN Order_Details as F ON O.orderId=F.orderId where empId = ? GROUP BY O.orderId, O.Fooditem_description</p>
	 * 
	 * @param empId - the specified empId.
	 * @return ArrayList&#60;String&#62; - contains all relevant data for the specified empId.
	 * @throws SQLException - SQL handling exception.
	 */
	public ArrayList<String> empIdSearch(int empId) throws SQLException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<String> results = new ArrayList<String>();
		try {
			String query ="SELECT O.orderId, tableNumber, Fooditem_description, COUNT(Fooditem_description) as quantity, special_requests, comments, orderTime, orderDate, empID FROM Order_Items as O JOIN Order_Details as F ON O.orderId=F.orderId where empId = ? GROUP BY O.orderId, O.Fooditem_description";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, empId);
			
			resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next()){
				
				results.add(Integer.toString(resultSet.getInt(1)));
				results.add(Integer.toString(resultSet.getInt(2)));
				results.add(resultSet.getString(3));
				results.add(Integer.toString(resultSet.getInt(4)));
				results.add(resultSet.getString(5));
				results.add(resultSet.getString(6));
				results.add(resultSet.getString(7));
				results.add(resultSet.getString(8));
				results.add(Integer.toString(resultSet.getInt(9)));

				results.add(",");
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			preparedStatement.close();
		}
		
		return results;
	}
	
	
}
