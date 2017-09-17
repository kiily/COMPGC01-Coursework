package uk.ac.ucl.cs.gc01.restaurant_billing_system.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.MenuManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.FoodItem;

/**
 * 
 * 
 * <p>The MenuController class provides the methods and elements of the GUI so 
 * that the user can act upon the elements defined in the FXML file for the edit menu screen
 * (Menu.fxml).
 * Accessibility to the FXML file is achieved with the following tag: @FXML. </p>
*	
 * <p> Note that this class should be added as a controller to SceneBuilder and all the
 * elements should have their fx:id specified in the code section. This applies to all other controllers.</p> 
 * 
 * <p>The class automatically initializes the data in the menuDataTable TableView from the database.</p>
 * 
 * <p>It provides the following methods:</p>
 * <ul>
 * 		<li><b>initialize(URL, ResourceBundle)</b>Initializes the GUI.</li>
 * 		<li> <b>goBack(ActionEvent)</b> - triggered by pressing the back button. Sends user back to AdminHome screen.</li>
 * 		<li> <b>remItem(ActionEvent)</b> - triggered by pressing the remove item button. Removes selected menu item from the database.</li>
 * 		<li> <b>addItem(ActionEvent)</b> - triggered by pressing the add item button. Creates a FoodItem and adds it to database.</li>
 * </ul>	
 * 
 * <b>References:</b>
 * <ul>
 * <li>http://code.makery.ch/blog/javafx-dialogs-official/</li>
 * <li>http://stackoverflow.com/questions/29090583/javafx-tableview-how-to-get-cells-data</li>
 * <li>http://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm#CJAGAAEE</li>
 * <li> https://www.youtube.com/watch?v=lpqZzHaGsyI</li>
 * <li>http://stackoverflow.com/questions/27806367/cant-populate-javafx-tableview-created-in-scenebuilder-2</li>
 * <li>http://stackoverflow.com/questions/32282230/fxml-javafx-8-tableview-make-a-delete-button-in-each-row-and-delete-the-row-a</li>
 * <li>https://gist.github.com/abhinayagarwal/9735744</li>
 * </ul> 
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class MenuController implements Initializable {


		@FXML
		private AnchorPane menuDataPane;
		@FXML
		private Label titleLabel;
		@FXML
		private Label specifyLabel;
		@FXML
		private Button addItemBtn;
		@FXML
		private Button remItemBtn;
		@FXML
		private Button backBtn;
		@FXML
		private TableView<FoodItem> menuDataTable;
		@FXML
		private TableColumn<FoodItem, Integer> itemColumn1;
		@FXML
		private TableColumn<FoodItem, String> itemColumn2;
		@FXML
		private TableColumn<FoodItem, String> itemColumn3;
		@FXML
		private TableColumn<FoodItem, Double> itemColumn4;
		@FXML
		private TextField foodItemDescriptionText;
		@FXML
		private TextField categoryText;
		@FXML
		private TextField rateText;
		
		
		//database manipulator
		private MenuManager menuManager = new MenuManager();
		
		public ObservableList<FoodItem> data;
		
		/**
		 * <p>Initializes the GUI and populates TableView.</p>
		 */
		@Override
		public void initialize(URL url, ResourceBundle rb){
			  		
			 
			try {
				data = menuManager.buildMenuData();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			//initializing TableView columns
			itemColumn1.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("itemId"));
			itemColumn2.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("foodItemDescription"));
			itemColumn3.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("category"));
			itemColumn4.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("rate"));
		
			
			menuDataTable.setItems(data);
			 
		}
		
		/**
		 *  
		 * <p>Takes the user (admin) from the current screen back to the admin home screen, opening the
		 * AdminHome.fxml file.</p>
		 * 
		 * @param click - pressing the back button.
		 */
		@FXML
		private void goBack(ActionEvent click){
	    	try{
				
				Parent root1 = FXMLLoader.load(getClass().getResource("AdminHome.fxml"));
		     
		        Stage stage = new Stage();
		        Scene scene = new Scene(root1);
		        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		        
				
				stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
		        stage.setTitle("Restaurant Billing System");
		        stage.setScene(scene);
		        stage.show();
		        
		        Stage stageToClose = (Stage) backBtn.getScene().getWindow();
		        stageToClose.close();
		        
		        
		        
		    	}catch(IOException e){
		    		e.printStackTrace();
		    	}
		}

		
		/**
		 * 
		 * <p>Removes the element selected in the menuDataTable TableView
		 * from the database. If no Employee is selected from the Table, an Alert will 
		 * be displayed.</p>
		 * 
		 * @param event - pressing the remove item button.
		 * @throws SQLException - SQL handling exception.
		 * @throws MalformedURLException - URL for icon is malformed.
		 */
		@FXML
		private void remItem(ActionEvent event) throws SQLException, MalformedURLException {
			
		FoodItem itemToRemove = menuDataTable.getSelectionModel().getSelectedItem();
		
		if(itemToRemove != null){
		int selectedIndex  = itemToRemove.getItemId();
	
						
			if(selectedIndex>= 0){
										
				menuManager.removeMenuData(selectedIndex);	
				
				//rebuild table after removing item
				data = menuManager.buildMenuData();
				menuDataTable.setItems(data);
			
				}
				
			
		}else{
			//No selection
			
			Alert alert = new Alert(AlertType.WARNING);
	   
							
			Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			
	        alert.setTitle("Restaurant Billing System - WARNING");
	        alert.setHeaderText("No Menu Item Selected");
	        alert.setContentText("Please select a Menu Item to remove.");

	        alert.showAndWait();
		}
			
	}

		/**
		 * 
		 * <p>Adds the element specified by the TextFields by creating a FoodItem object
		 * and adds it to the database. It then updates the menuDataTable TableView.
		 * If any of the text fields is not specifed, an Alert will be displayed.</p>
		 * 
		 * @param event - pressing the Add Item button.
		 * @throws SQLException - SQL handling exception.
		 * @throws MalformedURLException - URL for icon is malformed.
		 */
		@FXML	
		private void addItem(ActionEvent event) throws SQLException, MalformedURLException {
			
			
			String foodItemDescription = foodItemDescriptionText.getText();
	    	String category = categoryText.getText();
	    	String rateString = rateText.getText();
	    	
	    	
	    	
	    	
	    	if(foodItemDescription.equalsIgnoreCase("")|| category.equalsIgnoreCase("") || rateString.equalsIgnoreCase("")){
	    		
	    		Alert alert = new Alert(AlertType.ERROR);
		        
				
				Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
				tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
				
		        alert.setTitle("Restaurant Billing System - ERROR");
		        alert.setHeaderText("Invalid menu item");
		        alert.setContentText("Please enter a valid menu item to add.");

		        alert.showAndWait();
		        
	    	}else if(menuManager.isFoodItemDescription(foodItemDescription)==true){
	    		Alert alert = new Alert(AlertType.ERROR);
		        
				
				Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
				tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
				
		        alert.setTitle("Restaurant Billing System - ERROR");
		        alert.setHeaderText("Food Item already exists");
		        alert.setContentText("Please enter a valid menu item to add.");

		        alert.showAndWait();
	    	}
	    	else{
	    	
	    	double rate = Double.valueOf(rateString);
	    	
	    	
	    	FoodItem itemToAdd = new FoodItem(foodItemDescription,category,rate);
	    	menuManager.addMenuData(itemToAdd);
	    	data = menuManager.buildMenuData();
			menuDataTable.setItems(data);
	    	}
	    	
		}


}
