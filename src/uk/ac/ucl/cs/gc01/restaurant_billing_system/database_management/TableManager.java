package uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.Table;

/**
 * 
 * <p>The TableManager class connects to the database via its constructor. It provides methods that
 * search information from the Table_master table in the database.</p>
 * 
 * <b>It implements the following methods:</b>
 * <ul>
 * 		<li><b>updateTableRecord(Table)</b> - updates the Table_Master table in the database
 * as specified in the Table object passed to it.</li>
 * 		<li><b>updateNumberOfPeople(Table)</b> - updates the Table_Master table in the database
 * as specified in the Table object passed to it and updates the number of people.</li>
 * 		<li><b>updateCurrentOrderId(Table)</b> - updates the Table_Master table in the database
 * as specified in the Table object passed to it and updates the current OrderId attached to the Table.</li>
 * 		<li><b>getTableCapacity(Table)</b> - retrieves a Table's capacity from the Table_master table in the 
 * database as specified by the table number in the object.</li>
 * 		<li><b>getTableStatus(int)</b> - retrieves a Table's status from the Table_master table in the 
 * database as specified by the table number.</li>
 * 		<li><b>getNumberOfPeople(int)</b> - retrieves a the number of people sitting at a table from the Table_master table in the 
 * database as specified by the table number in the Table object.</li>
 * 		<li><b>getCurrentOrderId(Table)</b> - retrieves a Table's currentOrderId from the Table_master table in the 
 * database as specified by the table number in the Table object.</li>
 * 		<li><b>isTableNumber(int)</b> - checks whether a given table nummber exists in the database. If yes, it returns true 
 * if no, it returns false.</li>
 * </ul>
 * 
 * <b>References:</b>
 * <ul>
 * <li>https://www.youtube.com/watch?v=_eCn4pLw350&#38;list=PLS1QulWo1RIaUGP446_pWLgTZPiFizEMq&#38;index=33;</li>
 * <li>http://www.sqlitetutorial.net/sqlite-java/delete/</li>
 * <li>http://www.sqlitetutorial.net/sqlite-java/</li>
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class TableManager {

	private Connection connection;
	
	/**
	 * <b>Constructor:</b>
	 * <p>Connects to the database via the Connector static method from the SqliteConnection class.</p>
	 */
	public TableManager() {
		
		connection = SqliteConnection.Connector();
		
		//handling null if Exception throws
		if(connection == null) 
			System.exit(1);
		
		
	}
	
	/**
	 * <p>Updates the Table_master table in the database by updating the
     * information relative to the specified table number in the Table object passed as 
     * an argument. The other characteristics of the Table object are used to update
     * the information about that table in the database.</p>
     * 
     * 
     * <p><b>SQL query:</b> UPDATE Table_master SET status = ?, numberOfPeople =? where tableNumber = ?</p>
     * 
	 * @param table - Table object containing the info for updating the Table_master table.
	 * @throws SQLException - SQL handling exception.
	 */
	public void updateTableRecord(Table table) throws SQLException{

		PreparedStatement preparedStatement = null;
		String query = "UPDATE Table_master SET status = ?, numberOfPeople =? where tableNumber = ?";


		try {

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setBoolean(1, table.isStatus());
			preparedStatement.setInt(2, table.getNumberOfPeople());
			preparedStatement.setInt(3, table.getTableNumber());


			preparedStatement.executeUpdate();



		} catch (SQLException e) {
			e.printStackTrace();

		}finally{ 
			preparedStatement.close();


		}


	}

	/**
	 * 
     * <p>Updates the Table_master table in the database by updating the
     * the current number of people at a table as specified by table number in the object passed as 
     * an argument. The number of people is updated for the specified table number.</p>
	 * 
	 * <p><b>SQL query:</b> UPDATE Table_master SET numberOfPeople =? where tableNumber = ?</p>
	 * 
	 * @param table - Table object containing the info for updating the Table_master table.
	 * @throws SQLException - SQL handling exception.
	 */
	public void updateNumberOfPeople(Table table) throws SQLException{

	
		PreparedStatement preparedStatement = null;
		String query = "UPDATE Table_master SET numberOfPeople =? where tableNumber = ?";


		try {

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, table.getNumberOfPeople());
			preparedStatement.setInt(2, table.getTableNumber());

			preparedStatement.executeUpdate();
				
		

			} catch (SQLException e) {
				e.printStackTrace();
				
			}finally{ 
				preparedStatement.close();
				

			}


	}

	/**
     * <p>Updates the Table_master table in the database. Specifically it updates the
     * the current orderId at the table specified by table number in the object passed as 
     * an argument. This will allow the same orderId to be accessed when pressing
     * the table again and until the current order is closed.</p>
	 * 
	 * <p><b>SQL query:</b> UPDATE Table_master SET currentOrderId =? where tableNumber = ?</p>
	 * 
	 * @param table - Table object containing the info for updating the Table_master table.
	 * @throws SQLException - SQL handling exception.
	 */
	public void updateCurrentOrderId(Table table) throws SQLException{

		
		PreparedStatement preparedStatement = null;
		String query = "UPDATE Table_master SET currentOrderId =? where tableNumber = ?";


		try {

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, table.getCurrentOrderId());
			preparedStatement.setInt(2, table.getTableNumber());

			preparedStatement.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally{ 
			preparedStatement.close();


		}


	}


	/**
	 * 
     * <p>Queries the Table_master table in the database for
     * the table capacity of a given table number, as specified by the Table object
     * The capacity is extracted from the data and stored as an int which the method then
     * returns.</p>
	 * 
	 * <p><b>SQL query:</b> SELECT capacity FROM Table_master where tableNumber = ? "</p>
	 * 
	 * 
	 * @param table - Table object containing the info for querying the Table_master table.
	 * @return int - the capacity of the currently selected table.
	 * @throws SQLException - SQL handling exception.
	 */
	public int getTableCapacity(Table table) throws SQLException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT capacity FROM Table_master where tableNumber = ? ";
		int capacity = 0; 

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, table.getTableNumber());
			resultSet = preparedStatement.executeQuery();

			capacity = resultSet.getInt(1);



		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			preparedStatement.close();
		}

		return capacity;
	}

	
	/**
     * <p>Queries the Table_master table in the database for
     * the table status of a given table number, as specified by the tableNumber parameter passed
     * to the method. The status is extracted from the data and stored as a boolean which the method then
     * returns.</p>
	 * 
	 * <p><b>SQL query:</b> SELECT status FROM Table_master where tableNumber = ? </p>
	 * 
	 * 
	 * @param tableNumber - the currently selected table number.
	 * @return boolean - true if table is free; false if table is busy.
	 * @throws SQLException - SQL handling exception.
	 */
	public boolean getTableStatus(int tableNumber) throws SQLException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT status FROM Table_master where tableNumber = ? ";
		boolean status = true; 

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, tableNumber);
			resultSet = preparedStatement.executeQuery();

			status = resultSet.getBoolean(1);
			



		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			preparedStatement.close();
		}

		return status;
	}

	/**
	 * 
     * <p>Queries the Table_master table in the database for
     * the number of people at a given table number, as specified by the tableNumber parameter
     * passed to the method. The number of people is extracted from the data and stored as an int
     * which the method then returns.</p> 
     * 
     *  <p><b>SQL query:</b>  SELECT numberOfPeople FROM Table_master where tableNumber = ?</p>
     * 
     * 
	 * @param tableNumber - the currently selected table number.
	 * @return int - the number of people at the specified table.
	 * @throws SQLException - SQL handling exception.
	 */
	public int getNumberOfPeople(int tableNumber) throws SQLException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT numberOfPeople FROM Table_master where tableNumber = ? ";
		int numberOfPeople = 0; 

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, tableNumber);
			resultSet = preparedStatement.executeQuery();

			numberOfPeople = resultSet.getInt(1);



		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			preparedStatement.close();
		}

		return numberOfPeople;
	}


	/**
	 * 
     * <p>Queries the Table_master table in the database for
     * the current orderId at a given table number, as specified by the Table object passed
     * to the method. The current orderId is extracted from the data and stored as an int
     * which the method then returns.</p> 
     * 
     *  <p><b>SQL query:</b> SELECT currentOrderId FROM Table_master where tableNumber = ?</p>
     *  
     *  
	 * @param table  Table object containing relevant info to query the database.
	 * @return int - the current orderId at the specified Table.
	 * @throws SQLException - SQL handling exception.
	 */
	public int getCurrentOrderId(Table table) throws SQLException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT currentOrderId FROM Table_master where tableNumber = ? ";
		int currentOrderId = 0; 

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, table.getTableNumber());
			resultSet = preparedStatement.executeQuery();

			currentOrderId = resultSet.getInt(1);



		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			preparedStatement.close();
		}

		return currentOrderId;
	}

	/**
	 * <p>Takes a table number as an argument and queries the Table_master
	 * table in the database, to check whether the supplied table number exists. If it does, the method 
	 * returns true. If it does not, the method returns false.</p>
	 * 
	 * <p><b>SQL query:</b>SELECT tableNumber FROM Table_master</p>
	 * 
	 * 
	 * @param tableToCheck - the table number to check.
	 * @return boolean - true if table number in database; false otherwise.
	 * @throws SQLException - SQLException handling.
	 */
	public boolean isTableNumber(int tableToCheck) throws SQLException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT tableNumber FROM Table_master";
		boolean isTableNumber = false; 

		try {

			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			
			ArrayList<Integer> tableNumbers = new ArrayList<Integer>();
			
			while(resultSet.next()){
				int tableNumber = resultSet.getInt(1);
				tableNumbers.add(tableNumber);
			}
			 
			if(tableNumbers.contains(tableToCheck)){
				isTableNumber = true;
			}else{
				isTableNumber = false;
			}


		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			preparedStatement.close();
		}

		return isTableNumber;
	}


}//class end
