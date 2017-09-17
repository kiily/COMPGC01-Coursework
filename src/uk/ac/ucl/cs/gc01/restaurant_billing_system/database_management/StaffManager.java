package uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.app.LoginController;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.app.TablesController;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.Employee;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.Manager;

/**
 * 
 * 
 * <p>The StaffManager class connects to the database via its constructor. It provides methods that
 * manage the Staff section of the database.</p>
 * 
 * <b>It implements the following methods:</b>
 * <ul>
 * 		<li><b>isDatabaseConnected()</b> - checks whether the database is connected.</li>
 * 		<li><b>isLogin(String, String)</b> - checks whether a username-password pair corresponds to a login in the database.</li>
 * 		<li><b>isAdminLogin(String, String)</b> - checks whether a username-password pair corresponds to an admin login in the database.</li>
 * 		<li><b>retrieveConnectedEmpId(String, String)</b> - retrieves the employee id pair from the database, based on the username-password.</li>
 * 		<li><b>retrieveEmpName(int)</b> - retrieves the employee's firstname or surname from the databse, based on the empId.</li>
 * 		<li><b>getUsernames()</b> - extracts all the usernames from Staff table as an ArrayList of Strings.</li>
 * 		<li><b>getStaffType()</b> - returns the type of employee based on username and password (either Employee or Manager).</li>
 * 		<li><b>updateLastLogin</b> - updates the last login time in the Staff table for the currently connected employee.</li>
 * 		<li><b>buildStaffData()</b> - retrieves all the staff data from the database and stores it in an ObservableList of Employee type.</li>
 * 		<li><b>removeStaffData(int)</b> - deletes a specific member of staff from the database.</li>
 * 		<li><b>addStaffData(Employee)</b> - adds a member of staff to the database.</li>
 * </ul>
 * 
 * <b>References:</b>
 * <ul>
 * <li>https://www.youtube.com/watch?v=_eCn4pLw350&#38;list=PLS1QulWo1RIaUGP446_pWLgTZPiFizEMq&#38;index=33;</li>
 *<li> https://www.youtube.com/playlist?list=PLS1QulWo1RIaUGP446_pWLgTZPiFizEMq</li>
 * <li>http://www.sqlitetutorial.net/sqlite-java/delete/</li>
 * <li>http://www.sqlitetutorial.net/sqlite-java/</li>
 *  </ul>
 *  
 * @author Miguel Marin Vermelho
 *
 */
public class StaffManager {

	
	private Connection connection;
	
	
	/**
	 * <b>Constructor:</b>
	 * <p>Connects to the database via the Connector static method from the SqliteConnection class.</p>
	 */
	public StaffManager() {
		
		connection = SqliteConnection.Connector();
		
		//handling null if Exception throws
		if(connection == null) 
			System.exit(1);
		
		
	}
	
	/**
	 * @deprecated
	 * @return boolean - true  database connected; false  database not connected.
	 */
	public boolean isDatabaseConnected() {
		
		try {
			boolean isDatabaseConnected = connection.isClosed();
			return isDatabaseConnected;
			
		 } catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	
	/**
	 * <p>Queries the Staff table in the database to check whether
     * a username-password pair exists in the database; it returns a boolean; true if
     * there is a corresponding match, false if not.</p>
     * 
     * 
     * <p><b>SQL query:</b> SELECT * from Staff where username =? and password = ?</p>
     * 
	 * @param user - username of currently connected employee.
	 * @param pass - password of currently connected employee.
	 * @return boolean - true if login in database, else false.
	 * @throws SQLException - SQL handling exception.
	 */
	public boolean isLogin(String user, String pass) throws SQLException{
			PreparedStatement preparedStatement = null;
			ResultSet resultSet =null ;
			String query ="SELECT * from Staff where username =? and password = ?";
			
			boolean isLogin = false;
			try {
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, user);
				preparedStatement.setString(2, pass);
				
				resultSet = preparedStatement.executeQuery();
				
				if(resultSet.next()){
					isLogin =  true;
				}else{
					isLogin = false;
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				
			}finally{ 
				preparedStatement.close();
				resultSet.close();
			}
			return isLogin;
		}



	/**
	 * <p>Queries the Staff table in the database to check whether
     * a username-password pair exists in the database and corresponds to a Manager; it returns a boolean; true if
     * there is a corresponding match, false if not.</p>
     * 
     * 
     * <p><b>SQL query:</b> SELECT * from Staff where username =? and password = ? and type = 'Manager'</p>
	 * 
	 * @param user - username of currently connected employee.
	 * @param pass - password of currently connected employee.
	 * @return boolean - true if login in database, else false.
	 * @throws SQLException - SQL handling exception.
	 */
	public boolean isAdminLogin(String user, String pass) throws SQLException{
		
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT * from Staff where username =? and password = ? and type = 'Manager' ";
		boolean isAdminLogin = false;
		try {
		
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);
			
			
		
			resultSet = preparedStatement.executeQuery();
						
			
			if(resultSet.next()){
				isAdminLogin =  true;
			}else{
				isAdminLogin = false;

			}
		} catch (Exception e) {
			e.printStackTrace();
			

		}finally{ 
			preparedStatement.close();
			resultSet.close();
		}
		return isAdminLogin;
	}

	/**
	 * <p>Queries the Staff table in the database for
	 * all the stored usernames and returns them as an ArrayList of Strings.</p>
	 * 
	 * @return ArrayList&#60;String&#62; - array list containing all usernames in the database.
	 * @throws SQLException - SQLException in SQL.
	 */
	
	
	
	/**
     * <p>Queries the Staff table in the database to retrieve the
     * empId of the currently connected employee based on the username-password pair; it returns 
     * the empId as an int.</p>
     * 
     * 
     * <p><b>SQL query:</b> select empID, type from Staff where username =? and password = ?</p>
     * 
	 * @param username - username of currently connected employee.
	 * @param password - password of currently connected employee.
	 * @return int - empId of the currently connected Employee.
	 * @throws SQLException - SQL handling exception.
	 */
	public int retrieveConnectedEmpId(String username, String password) throws SQLException{
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet =null ;
			String query ="select empID, type from Staff where username =? and password = ?";
			int empID =0 ;
			try {
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					
					empID = resultSet.getInt(1);
					TablesController.setStaffType(resultSet.getString(2));
										
					
				}
				
			}catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				
			}finally{ 
				preparedStatement.close();
				resultSet.close();
			}
			return empID;

				
			
		}
		
		/**
		 * 
		 * <p>Queries the Staff table in the database to retrieve the
		 * firstname and surname of the currently connected employee based on its empId; it returns 
		 * a String[] ={firstname, surname}.</p>
		 * 
		 * 
		 * <p><b>SQL query:</b> select firstname, surname from Staff where empID =?</p>
		 * 
		 * @param empId - empId of currently connected employee.
		 * @return String[] - String[2] containing the employee's firstname and surname.
		 * @throws SQLException - SQL handling exception.
		 */
		public String[] retrieveEmpName(int empId) throws SQLException{
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet =null ;
			String query ="select firstname, surname from Staff where empID =? ";
			
			String[] fullName = new String[2];
			try {
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, empId);
				
				
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					
					String firstname = resultSet.getString(1);
					String surname = resultSet.getString(2);
					
					
					fullName[0]= firstname;
					fullName[1]= surname;
				
					
					
				}
				
			}catch (SQLException e) {
				e.printStackTrace();
				
				return null;
				
			}finally{ 
				preparedStatement.close();
				resultSet.close();

			}
			return fullName;
			

			
		}	
		
		
		public ArrayList<String> getUsernames() throws SQLException{
			
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet =null ;
			String query ="SELECT username from Staff";
			
			ArrayList<String> usernames = new ArrayList<String>();
			try {
			
				preparedStatement = connection.prepareStatement(query);
				
				
			
				resultSet = preparedStatement.executeQuery();
							
				while(resultSet.next()){
					String username = resultSet.getString(1);
					usernames.add(username);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				

			}finally{ 
				preparedStatement.close();
				resultSet.close();
			}
			return usernames;
		}
		
		
		/**
		 * 
		 * <p>Queries the Staff table in the database to retrieve the
		 * type of the currently connected employee using its username and password; it returns 
		 * a String containing the employee's type.</p>
		 * 
		 * 
		 * <p><b>SQL query:</b> SELECT type FROM Staff where username = ? and password = ?</p>
		 * 
		 * @return String  the type of the currently connected employee.
		 * @throws SQLException - SQL handling exception.
		 */
		public String getStaffType() throws SQLException{
			
			String username = LoginController.username;
			String password = LoginController.password;
			
			String type ="";
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet =null ;
			String query ="SELECT type FROM Staff where username = ? and password = ?";
			
					try {
						
						preparedStatement = connection.prepareStatement(query);
						preparedStatement.setString(1, username);
						preparedStatement.setString(2, password);
						resultSet = preparedStatement.executeQuery();
						
						type = resultSet.getString(1);					
						
						
						
						} catch (Exception e) {
						e.printStackTrace();
						
						}finally{
							preparedStatement.close();
						}
			return type;
		}
		
		
		/**
		 * <p>Updates the lastLogin column of the Staff table in the database for the currently
		 * connected employee</p>
		 * 
		 * <p><b>SQL query:</b>UPDATE Staff SET lastLogin =? where empID = ?</p>
		 * 
		 * @throws SQLException - SQL handling exception.
		 */
		public void updateLastLogin() throws SQLException{
			
			String lastLogin = LoginController.loginTime;
			int empId = LoginController.empId;
			
			PreparedStatement preparedStatement = null;
			String query = "UPDATE Staff SET lastLogin =? where empID = ?";


			try {

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setString(1, lastLogin);
				preparedStatement.setInt(2, empId);

				preparedStatement.executeUpdate();


			} catch (SQLException e) {
				e.printStackTrace();
				
			}finally{ 
				preparedStatement.close();


			}
		}
		
		

		
		
		/**
		 * <p>Queries the Staff table and retrieves all the stored data
		 * ; it returns an ObservableList of Employee objects containing the data.</p>
		 * 
		 * 
		 * <p><b>SQL query:</b> select * from Staff</p>
		 * 
		 * @return ObservableList&#60;Employee&#62; - a List containing all the data from the Staff table in
		 * the database.
		 * @throws SQLException - SQL handling exception.
		 */
		public ObservableList<Employee> buildStaffData() throws SQLException{
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet =null ;
			String query ="select * from Staff";
			
			ObservableList<Employee> data = FXCollections.observableArrayList();
			
			try {
				
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					
					if(resultSet.getString(6).equalsIgnoreCase("Manager")){
						
						Manager man = new Manager(
								
								resultSet.getString(2), 
								resultSet.getString(3),
								resultSet.getString(4),
								resultSet.getString(5)
								
								
								);
						
						man.setLastLogin(resultSet.getString(7));
						man.setEmpNumber(resultSet.getInt(1));
						data.add(man);
						
						
					}else if(resultSet.getString(6).equalsIgnoreCase("Employee")){
					
						
						
						Employee emp = new Employee(
								
								
								resultSet.getString(2), 
								resultSet.getString(3),
								resultSet.getString(4),
								resultSet.getString(5)
								
								);
						
						emp.setLastLogin(resultSet.getString(7));
						emp.setEmpNumber(resultSet.getInt(1));
						
								
						
					data.add(emp);
					}
					
					
				}
				
				return data;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
				
			}finally{
				preparedStatement.close();
				resultSet.close();
			}
		}
		
		
	/**
	 *
	 *
	 * <p>Deletes data from the Staff table as specified
	 * by the index selected from the empDataTable TableView.</p>
	 * 
	 * <p><b>SQL query:</b> DELETE FROM Staff WHERE empID = ?</p>
	 * 
	 * @param selectedIndex - the element selected from the empDataTable TableView.
	 * @throws SQLException - SQL handling exception.
	 */
		public void removeStaffData(int selectedIndex) throws SQLException{
			

			
			PreparedStatement preparedStatement = null;
			String query ="DELETE FROM Staff WHERE empID = ?";
			
			try {
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, selectedIndex);
			
				preparedStatement.executeUpdate();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			
				
			}finally{ 
				preparedStatement.close();
				
			}
			
			
			
		}
		
		/**
		 * 
		 * <p>Inserts data into the Staff table as specified
		 * by the Employee object passed as an argument. This corresponds to a row in the
		 * database as each of the Employee's instance variables have a corresponding column.</p>
		 * 
		 * <p><b>SQL query:</b> INSERT INTO Staff(firstname,surname,username,password,type) VALUES(?,?,?,?,?)</p>
		 * 
		 * @param empToAdd - Employee object to add to Staff table in database.
		 * @throws SQLException - SQL handling exception.
		 */
		public void addStaffData(Employee empToAdd) throws SQLException{
			
			PreparedStatement preparedStatement = null;
			//ResultSet resultSet =null ;
			String query ="INSERT INTO Staff(firstname,surname,username,password,type) VALUES(?,?,?,?,?)";
			
			try {
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, empToAdd.getFirstname());
				preparedStatement.setString(2, empToAdd.getSurname());
				preparedStatement.setString(3, empToAdd.getUsername());
				preparedStatement.setString(4, empToAdd.getPassword());
				preparedStatement.setString(5, empToAdd.getType());
			
				preparedStatement.executeUpdate();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				
			}finally{ 
				preparedStatement.close();
				
			}
			
		}
}
