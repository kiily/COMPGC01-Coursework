
package uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.FoodItem;

/**
 * 
 * 
 * <p>The MenuManager class connects to the database via its constructor. It provides methods that
 * manage the Fooditems_Details section of the database.</p>
 * 
 * <b>It implements the following methods:</b>
 * <ul>
 * 		<li><b>buildMenuData()</b> - retrieves all the menu data (Fooditem_Details) from the database and stores it in and ObservableList of FoodItem type.</li>
 * 		<li><b>removeMenu(int)</b> - deletes a specific menu item from the database.</li>
 * 		<li><b>addMenuData(Employee)</b> - adds a food item to the menu in the database.</li>
 * 		<li><b>isFoodItemDescription(String)</b> - Checks whether a given food item exists in the database by querying the Food_Items table.</li>
 *
 * </ul>
 * 
 * <b>References:</b>
 * <ul>
 * <li>https://www.youtube.com/watch?v=_eCn4pLw350&#38;list=PLS1QulWo1RIaUGP446_pWLgTZPiFizEMq&#38;index=33;</li>
 *<li> http://www.sqlitetutorial.net/sqlite-java/delete/</li>
 * <li>http://www.sqlitetutorial.net/sqlite-java/</li>
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class MenuManager {

	
	private Connection connection;
	
	/**
	 * <b>Constructor:</b>
	 * <p>Connects to the database via the Connector static method from the SqliteConnection class.</p>
	 */
	public MenuManager() {
		
		connection = SqliteConnection.Connector();
		
		//handling null if Exception throws
		if(connection == null) 
			System.exit(1);
		
		
	}
	
	/**
	 *
	 * 
	 * <p>Queries the Fooditem_Details table and retrieves all the stored data
	 * ;it returns an ObservableList of FoodItems objects containing the data.</p>
	 * 
	 * 
	 * <p><b>SQL query:</b> select * from Fooditems_Details</p>
	 * 
	 * @return ObservableList&#60;Employee&#62; - a List containing all the data from the Staff table in
	 * the database.
	 * @throws SQLException - SQL handling exception.
	 *
	 * 
	 */
	public ObservableList<FoodItem> buildMenuData() throws SQLException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT * from Fooditems_Details";

		ObservableList<FoodItem> itemData = FXCollections.observableArrayList();

		try {

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()){


				FoodItem item = new FoodItem(


						resultSet.getString(2), 
						resultSet.getString(3),
						resultSet.getDouble(4)


						);


				item.setItemId(resultSet.getInt(1));
				itemData.add(item); 
				
			}

			


		} catch (Exception e) {
			e.printStackTrace();
			

		}finally{
			preparedStatement.close();
		}
		return itemData;
	}


	/**
	 *
	 * <p>Deletes data from the Fooditems_Details table as specified
	 * by the index selected from the menuDataTable TableView.</p>
	 * 
	 * <p><b>SQL query:</b> DELETE FROM Fooditems_Details WHERE foodItem_id = ?</p>
	 * 
	 * @param selectedIndex - the element selected from the menuDataTable TableView.
	 * @throws SQLException - SQL handling exception.
	 * 
	 */
	public void removeMenuData(int selectedIndex) throws SQLException{



		PreparedStatement preparedStatement = null;
		String query ="DELETE FROM Fooditems_Details WHERE foodItem_id = ?";

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
	 * <p>Inserts data into the Fooditems_details table as specified
	 * by the FoodItem object passed as an argument. This corresponds to a row in the
	 * database as each of the FoodItem's instance variables have a corresponding column.</p>
	 * 
	 * <p><b>SQL query:</b> INSERT INTO Fooditems_details(Fooditem_description,category,rate) VALUES(?,?,?)"</p>
	 * 
	 * @param itemToAdd - FoodItem object to add to Fooditems_details table in database.
	 * @throws SQLException - SQL handling exception.
	 * 
	 */
	public void addMenuData(FoodItem itemToAdd) throws SQLException{

		PreparedStatement preparedStatement = null;
		String query ="INSERT INTO Fooditems_details(Fooditem_description,category,rate) VALUES(?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, itemToAdd.getFoodItemDescription());
			preparedStatement.setString(2, itemToAdd.getCategory());
			preparedStatement.setDouble(3, itemToAdd.getRate());


			preparedStatement.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
			

		}finally{ 
			preparedStatement.close();

		}

	}
	
	/**
	 * <p>Checks whether a given food item exists in the database by querying the Food_Items
	 * table. The method returns true if a match is found and false otherwise.</p>
	 * 
	 * <p><b>SQL query:</b> SELECT Fooditem_description from Order_Items where Fooditem_description = ?</p>
	 * 
	 * @param foodItemDescription - the food item to check against the database.
	 * @return boolean - true if item is in the database, false otherwise.
	 * @throws SQLException - SQL handling exception.
	 */
	public boolean isFoodItemDescription(String foodItemDescription) throws SQLException{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null ;
		String query ="SELECT Fooditem_description from Order_Items where Fooditem_description = ?";
		boolean isFoodItemDescription = false;
		try {
		
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, foodItemDescription);
			
			
			
		
			resultSet = preparedStatement.executeQuery();
						
			
			if(resultSet.next()){
				isFoodItemDescription =  true;
			}else{
				isFoodItemDescription = false;

			}
		} catch (Exception e) {
			e.printStackTrace();
			

		}finally{ 
			preparedStatement.close();
			resultSet.close();
		}
		return isFoodItemDescription;
	}

}
