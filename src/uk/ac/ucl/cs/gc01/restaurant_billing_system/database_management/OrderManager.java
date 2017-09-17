package uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.app.LoginController;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.OrderDetails;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.OrderItems;

/**
 *
 * 
 * <p>The Order Manager class connects to the database via its constructor. It provides methods that
 * manage the Order_Details and Order_Items sections of the database.</p>
 * 
 * <b>It implements the following methods:</b>
 * <ul>
 * 		<li><b>getItemDescriptionToAdd()</b> - queries database for all foodItem descriptions and returns them in
 * an ObservableList</li>
 * 		<li><b>getItemDescriptionToRemove(int)</b> - queries the database for all foodItem descriptions that are available in the
 * specified orderId and returns them as Stings in an ObservableList </li>
 * 		<li><b>addOrderItems(String, int, int, String, String)</b> - inserts a foodItem into the Order_Items table</li>
 * 		<li><b>removeOrderItems()</b> - deletes a foodItem from the Order_Items table</li>
 * 		<li><b>generateOrderRecord(int, int)</b> - generates a new order record in the Order_Details table and returns a timestamped Order
 * Details object.</li>
 * 		<li><b>destroyOrderRecord(int, int)</b> - eliminates the specified order from the Order_Details and Order_Items tables</li>
 * 		<li><b>buildOrderItemsData()</b> - retrieves all the OrderItems JOIN foodItemDetails data from the Order_Items and Fooditem_Details tables and stores it in an ObservableList of OrderItems type</li>
 *		<li><b>buildOrderDetailsData(OrderDetails, int)</b> - retrieves all the OrderDetails from the Order_Details table as an observable list of OrderDetails</li>
 * 		<li><b>calculateOrderTotal(int)</b> - queries the database for the rate and quantity of the foodItems
 * in the database and computes the total price of the order</li>
 * 		<li><b>getOrderId(OrderDetails, int)</b> - queries the Order_Details table for the orderId of a specified order and tableNumber</li>
 * 		<li><b>exportData(int)</b> - retrieves the information for the specified orderId to export and formats it
 * in preparation for output</li>
 * </ul>
 * 
 * <b>References:</b>
 * <ul>
 * <li>https://www.youtube.com/watch?v=_eCn4pLw350&#38;list=PLS1QulWo1RIaUGP446_pWLgTZPiFizEMq&#38;index=33;</li>
 *<li> https://www.youtube.com/playlist?list=PLS1QulWo1RIaUGP446_pWLgTZPiFizEMq</li>
 *<li> http://www.sqlitetutorial.net/sqlite-java/delete/</li>
 * <li>http://www.sqlitetutorial.net/sqlite-java/</li>
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class OrderManager {

	
	private Connection connection;
	
	/**
	 * <b>Constructor:</b>
	 * <p>Connects to the database via the Connector static method from the SqliteConnection class.</p>
	 */
	public OrderManager() {
		
		connection = SqliteConnection.Connector();
		
		//handling null if Exception throws
		if(connection == null) 
			System.exit(1);
		
		
	}
	

	
	/**
	 * 
	 * <p>Queries the database and extracts the menu item descriptions from the table. 
	 * These are added as Strings to an ObservableList which the method returns.</p>
	 * 
	 *  <p><b>SQL query:</b> select Fooditem_description from Fooditems_Details</p>
	 * 
	 * @return ObservableList&#60;String&#62; - list containing the description of every item in the menu.
	 * @throws SQLException - SQL handling exception.
	 */
	public ObservableList<String> getItemDescriptionToAdd() throws SQLException{
		
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="select Fooditem_description from Fooditems_Details";
		
		ObservableList<String> data = FXCollections.observableArrayList();
		
		try {
			
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				data.add(resultSet.getString(1));
			}
			

		}catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			preparedStatement.close();
		}
		
		return data;
	}
	
	/**
	
	 * 
	 * <p> Takes the current orderId queries the
	 * database for the foodItems which are attached to the specified orderId. It
	 * returns them as Strings in an ObservableList.</p>
	 * 
	 * 
	 * <p><b>SQL query:</b> select Fooditem_description from Order_Items where orderId =? GROUP BY Fooditem_Description</p>
	 * 
	 * @param orderID - the current order Id.
	 * @return ObservableList&#60;String&#62; - an observable list containing the available
	 * items to remove from the current order.
	 * @throws SQLException - SQL handling exception.
	 */
	public ObservableList<String> getItemDescriptionToRemove(int orderID) throws SQLException{
		
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="select Fooditem_description from Order_Items where orderId =? GROUP BY Fooditem_Description";
		
		ObservableList<String> data = FXCollections.observableArrayList();
		
		try {
			String orderId = Integer.toString(orderID);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, orderId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				data.add(resultSet.getString(1));
			}
			

		}catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			preparedStatement.close();
			resultSet.close();
		}
		return data;
		
	}
		
	/**
	 * <p>Inserts a foodItem into the Order_Items table with the
	 * specifications passed by the parameters.</p> 
	 *  
	 *  <p><b>SQL query:</b> INSERT INTO Order_Items(Fooditem_description, orderId, comments, special_requests) VALUES(?,?,?,?)"</p>
	 *  
	 * @param foodItemDescription - String representation of the foodItemDescription to add.
	 * @param quantity - quantity specified in spinner.
	 * @param orderNum - orderNum specified in orderNumLabel.
	 * @param comments - comments specified in comments TextArea.
	 * @param specialRequests - specialRequests specified in specialRequests TextArea.
	 * @throws SQLException - SQL handling exception.
	 */
	public void addOrderItems(String foodItemDescription, int quantity, int orderNum, String comments, String specialRequests) throws SQLException{
		
		PreparedStatement preparedStatement = null;
		
		
		while(quantity >0 ){
		String query ="INSERT INTO Order_Items(Fooditem_description, orderId, comments, special_requests) VALUES(?,?,?,?)";
		
		
		try {

			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, foodItemDescription);
			preparedStatement.setInt(2, orderNum);
			preparedStatement.setString(3, comments);
			preparedStatement.setString(4, specialRequests);


			preparedStatement.executeUpdate();
			
			

		} catch (SQLException e) {
			e.printStackTrace();
			

		}finally{ 
			preparedStatement.close();
			quantity--;
	
		}
		
		}	
		
		
	}

	/**
	 * <p>Deletes a foodItem from the Order_Items table with the
	 * specifications passed by the parameters.</p> 
	 *  
	 *  <p><b>SQL query:</b> DELETE FROM Order_Items where orderItemID IN (SELECT orderItemID FROM Order_Items where Fooditem_description = ? and orderID = ? GROUP BY Fooditem_description)"</p>
	 * @param cb - remItemsChoiceBox containing the foodItem description.
	 * @param quantity - quantity specified by remItemSpinner.
	 * @param orderNum - the current orderId.
	 * @throws SQLException - SQL handling exception.
	 */
	public void removeOrderItems(String cb, int quantity, int orderNum) throws SQLException{
		
		PreparedStatement preparedStatement = null;
				
		while(quantity >0 ){
		String query ="DELETE FROM Order_Items where orderItemID IN (SELECT orderItemID FROM Order_Items where Fooditem_description = ? and orderID = ? GROUP BY Fooditem_description)";
		
		
		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, cb);
			
			preparedStatement.setInt(2, orderNum);
	


			preparedStatement.executeUpdate();
			
			//connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		

		}finally{ 
			preparedStatement.close();
			quantity--;
		}
		
		}
		
		
	}
	

	/**
	 * <p> Takes the current table number and empId
	 * and generates a record of the order in the Order_Details table in the database.
	 * It returns an OrderDetails object which carries the timestamp at which the 
	 * order was created.</p>
	 * 
	 * <p><b>SQL query:</b>INSERT INTO Order_Details(tableNumber, orderTime, orderDate, empID) VALUES(?,?,?,?)</p>
	 * 
	 * 
	 * @param tableNumber - the currently selected table number.
	 * @param currentEmpID - the currently connected Employee's id.
	 * @return OrderDetails - OrderDetails object.
	 * @throws SQLException - SQL handling exception.
	 */
	public OrderDetails generateOrderRecord(int tableNumber, int currentEmpID) throws SQLException{
		
		//create new object here so that time is accurate
		OrderDetails order = new OrderDetails();
		
		PreparedStatement preparedStatement1 = null;
		String query ="INSERT INTO Order_Details(tableNumber, orderTime, orderDate, empID) VALUES(?,?,?,?)";
		
		
		try {

			preparedStatement1 = connection.prepareStatement(query);
			

			preparedStatement1.setInt(1, tableNumber);
			preparedStatement1.setString(2, order.getOrderTime());
			preparedStatement1.setString(3, order.getOrderDate());
			preparedStatement1.setInt(4, currentEmpID);
			
			
			preparedStatement1.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		

		}finally{ 
			preparedStatement1.close();
			

		}
		return order;
	}
	
	/**
	 * <p> Takes the current orderId and deletes the order's records
	 * from both the Order_Details and Order_Items tables.</p>
	 * 
	 * 
	 * 
	 * <p><b>SQL query1:</b>DELETE FROM Order_Details where orderId =?</p>
	 * <p><b>SQL query2:</b>DELETE FROM Order_Items where orderId =?</p>
	 * 
	 * @param orderID - the id of the current order.
	 * @throws SQLException - SQL handling exception.
	 */
	public void destroyOrderRecord(int orderID) throws SQLException{
		

		
		PreparedStatement preparedStatement = null;
		String query ="DELETE FROM Order_Details where orderId =?";
		
		PreparedStatement preparedStatement2 = null;
		String query2 ="DELETE FROM Order_Items where orderId =?";
		
		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, orderID );
			
			preparedStatement.executeUpdate();
			
			preparedStatement2 = connection.prepareStatement(query2);
			preparedStatement2.setInt(1, orderID );
			
			preparedStatement2.executeUpdate();
			
			

		} catch (SQLException e) {
			e.printStackTrace();
			

		}finally{ 
			preparedStatement.close();

		}
	}

	
	
	

	
	/**
	 * <p>Queries a newly generated table which joins
	 * the Fooditem_Details and Order_Items tables of the database. It the retrieves all the stored data
	 * ; it returns an ObservableList of OrderItems objects containing the data.</p>
	 * 
	 * 
	 * <p><b>SQL query:</b> SELECT orderId, O.Fooditem_description, COUNT(F.Fooditem_description) as quantity, rate, special_requests, comments FROM Order_Items as O JOIN Fooditems_Details as F ON O.Fooditem_Description=F.Fooditem_Description  where orderId = ? GROUP BY O.orderId, O.Fooditem_description</p>
	 * 
	 * 
	 * @param orderNum - the currently selected orderId.
	 * @return ObservableList&#60;OrderItems&#62; - a List containing all the data from the joined Order_Items and
	 * Fooditem_Details tables in the database.
	 * @throws SQLException - SQL handling exception.
	 */
	public ObservableList<OrderItems> buildOrderItemsData(int orderNum) throws SQLException{
		
			
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT orderId, O.Fooditem_description, COUNT(F.Fooditem_description) as quantity, rate, special_requests, comments FROM Order_Items as O JOIN Fooditems_Details as F ON O.Fooditem_Description=F.Fooditem_Description  where orderId = ? GROUP BY O.orderId, O.Fooditem_description";
		
		ObservableList<OrderItems> itemData = FXCollections.observableArrayList();
		
		try {
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, orderNum);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
						
					
					OrderItems item = new OrderItems(
							
							
							resultSet.getString(2), 
							resultSet.getInt(3),
							resultSet.getDouble(4),
							resultSet.getString(5),
							resultSet.getString(6)
							
							
							);
					
					
									
					 
				itemData.add(item); 
				
				}
				
			
			
			
			} catch (Exception e) {
			e.printStackTrace();
			
			
		}finally{
			preparedStatement.close();
			resultSet.close();
		}
		return itemData;
		
	}
	
	
	
	
	/**
	 * 
	 * <p>Queries the Order_Details table and retrieves all the stored data
	 * ; it returns an ObservableList of OrderDetails objects containing the data.</p>
	 * 
	 * 
	 * <p><b>SQL query:</b> select * from Order_Details</p>
	 * 
	 * @return ObservableList&#60;OrderDetails&#62; - observableList containing the OrderDetails data.
	 * @throws SQLException - SQL handling exception.
	 */
	public ObservableList<OrderDetails> buildOrderDetailsData() throws SQLException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT * from Order_Details";
		
		ObservableList<OrderDetails> orderDetailsData = FXCollections.observableArrayList();
		
		
		try {
			
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
						
					
					OrderDetails orderDetails= new OrderDetails();
					
					orderDetails.setOrderId(resultSet.getInt(1));
					orderDetails.setTableNumber(resultSet.getInt(2));
					orderDetails.setOrderTime(resultSet.getString(3));
					orderDetails.setEmpId(resultSet.getInt(4));
					orderDetails.setOrderDate(resultSet.getString(5));
					
					
					
				orderDetailsData.add(orderDetails);
				}
				
			
			
			
			} catch (Exception e) {
			e.printStackTrace();
			
			
		}finally{
			preparedStatement.close();
		}
		return orderDetailsData;
	}
	
	/**
	 * <p>Takes the current orderId and creates a new table
	 * JOINING the Fooditem_Details and Order_Items tables and getting the quantity and
	 * rate of the foofItems in the specified order. The total is computed as a double and 
	 * returned.</p>
	 * 
	 * <p><b>SQL query:</b> SELECT rate, COUNT(F.Fooditem_description) as quantity, o.Fooditem_description FROM Order_Items as O JOIN Fooditems_Details as F ON O.Fooditem_Description=F.Fooditem_Description  where orderId = ? GROUP BY O.orderId, O.Fooditem_description</p>
	 * 
	 * @param orderId - the currently selected orderId.
	 * @return double total - the total price of the order.
	 * @throws SQLException - SQL handling exception.
	 */
	public double calculateOrderTotal(int orderId) throws SQLException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT rate, COUNT(F.Fooditem_description) as quantity, o.Fooditem_description FROM Order_Items as O JOIN Fooditems_Details as F ON O.Fooditem_Description=F.Fooditem_Description  where orderId = ? GROUP BY O.orderId, O.Fooditem_description";
		
		double total =0;
		try {
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, orderId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				double rate = resultSet.getDouble(1);
				double quantity = (double) resultSet.getInt(2);
				
				//calculate total
				total = total + (rate*quantity);
				
			}
			
			
			} catch (Exception e) {
			e.printStackTrace();
			
			}finally{
				preparedStatement.close();
			}
		return total;
	}
	
	/**
	 *<p>Queries the database for the orderId based on a timestamped
	 * OrderDetails object and the current table number. It returns the orderId corresponding
	 * to the specified order details from the Order_Details table.</p>
	 * 
	 * <p><b>SQL query:</b>SELECT orderId FROM Order_Details where tableNumber=? and orderTime =? and orderDate=? and empID =?</p>
	 * 
	 * @param order - OrderDetails object.
	 * @param tableNumber - the currently selected tableNumber.
	 * @return int orderId - the corresping to a specified order.
	 * @throws SQLException - SQL handling exception.
	 */
	public int getOrderId(OrderDetails order, int tableNumber) throws SQLException{
		
		int orderId =0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet1 = null;
		String query ="SELECT orderId FROM Order_Details where tableNumber=? and orderTime =? and orderDate=? and empID =?";
		
		
		int currentEmpId = LoginController.empId;
		
		
		try {
			
		
			preparedStatement = connection.prepareStatement(query);
			
			//used to retrieve the order ID to print it to the field
			preparedStatement.setInt(1, tableNumber);
			preparedStatement.setString(2, order.getOrderTime());
			preparedStatement.setString(3, order.getOrderDate());
			preparedStatement.setInt(4, currentEmpId);

			
			resultSet1 = preparedStatement.executeQuery();
			
			
		
			orderId =resultSet1.getInt(1);
			
			

		} catch (SQLException e) {
			e.printStackTrace();
			

		}finally{ 
			preparedStatement.close();
			resultSet1.close();
			
		}
	
		return orderId;
	}
	
	
	/**
	 * <p>Takes the selected orderId from the orderDetailsDataTable
	 * and queries the database to find all the information about that orderId from the
	 * joined Order_Details and Order_Items tables in the database. The data is then formatted
	 * to prepare export as a .csv file.</p>
	 * 
	 * <p><b>SQL query:</b> SELECT O.orderId, tableNumber, Fooditem_description, COUNT(Fooditem_description) as quantity, special_requests, comments, orderTime, orderDate, empID FROM Order_Items as O JOIN Order_Details as F ON O.orderId=F.orderId where O.orderId = ?  GROUP BY O.orderId, O.Fooditem_description</p>
	 * 
	 * @param orderId - selected orderId to export.
	 * @return ArrayList&#60;String&#62; - ArrayList containing the formatted order elements.
	 * retrieved from the database.
	 * @throws SQLException - SQL handling exception.
	 */
	public ArrayList<String> exportData(int orderId) throws SQLException{
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
	
	
	
}

