package uk.ac.ucl.cs.gc01.restaurant_billing_system.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.SearchManager;

/**
 *  
 * 
 * <p>The OrderSearchController class provides the methods and elements of the GUI so 
 * that the user can act upon the elements defined in the FXML file for the order search screen
 * (OrderSearch.fxml).
 * Accessibility to the FXML file is achieved with the following tag: @FXML. </p>
*	
 * <p> Note that this class should be added as a controller to SceneBuilder and all the
 * elements should have their fx:id specified in the code section. This applies to all other controllers.</p> 
 * 
 * 
 * <p>It provides the following methods:</p>
 * <ul>
 * 		<li><b>initialize(URL, ResourceBundle)</b>Initializes the GUI.</li>
 * 		<li> <b>searchOrder(ActionEvent)</b> - triggered by pressing the search order button. Searches the database based on a criterion and 
 * a specific instance of that criterion and prints results to textArea.</li>
 * 		<li> <b>goBack(ActionEvent)</b> - triggered by pressing the back button. Sends user back to the Order History screen.</li>
 * </ul>	
 * 
 * <b>References:</b>
 * <ul>
 *  <li>http://code.makery.ch/blog/javafx-dialogs-official/</li>
 * <li>http://stackoverflow.com/questions/28177370/how-to-format-localdate-to-string</li>
 * </ul>
 * @author Miguel Marin Vermelho
 */
public class OrderSearchController implements Initializable {

	

	@FXML
	private ChoiceBox<String> criteria;
	@FXML
	private TextField criteriaTextField;
	@FXML
	private TableView<String> searchResults;
	@FXML
	private TableColumn<String, String> orderIdColumn;
	@FXML
	private TableColumn<String, String> tableNumberColumn;
	@FXML
	private TableColumn<String, String> itemDescriptionColumn;
	@FXML
	private TableColumn<String, String> quantityColumn;
	@FXML
	private TableColumn<String, String> specialRequestsColumn;
	@FXML
	private TableColumn<String, String> commentsColumn;
	@FXML
	private TableColumn<String, String> orderDateColumn;
	@FXML
	private TableColumn<String, String> orderTimeColumn;
	@FXML 
	private TableColumn<String, String> empIdColumn;
	@FXML
	private TextArea searchTextArea;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button backBtn;
	@FXML
	private Button searchBtn;
	
	//database manipulators
	private SearchManager searchManager = new SearchManager();
	
	private ObservableList<String> criteriaToAdd = FXCollections.observableArrayList("orderId", "Table Number", "FoodItem","empId");
	
	/**
	 * <p>Initializes the GUI and choice box.</p>
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//initialize criteria choice box
		criteria.setItems(criteriaToAdd);
		
		
	}
	
	/**
	 * <p> Takes the currently selected criterion form choice
	 * box and a second criterion (corresponding to an instance of the first; e.g. orderId and 2)
	 * as well as a specific date to retrieve data from the database, with the specified criteria. 
	 * If one of the criteria fields is empty and Alert is displayed. The operation will be succesful
	 * even if there is no selection in the DatePicker. In this situation, the orders are displayed 
	 * but the date filter is disabled.</p>
	 * 
	 * <p>If no results are returned from our search, an Alert will appear to inform the user.</p>
	 * 
	 * @param event - pressing search order items.
	 * @throws SQLException - SQL handling exception.
	 * @throws MalformedURLException - URL for icon is malformed.
	 */
	@FXML
	private void searchOrder(ActionEvent event) throws SQLException, MalformedURLException{
		
		searchTextArea.clear();

		String searchCriteria1 = criteria.getSelectionModel().selectedItemProperty().get();
		String searchCriteria2 = criteriaTextField.getText();


		String dateStamp ="empty";


		if(criteriaTextField.getText().equals("")){
			Alert alert1 = new Alert(AlertType.WARNING);
			
			
			Stage tempStage = (Stage) alert1.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			
			alert1.setTitle("Restaurant Billing System - WARNING");
			alert1.setHeaderText("Search status - search Failed");
			alert1.setContentText("Please make sure you have provided all criteria!");

			alert1.showAndWait();
		}

		LocalDate tempDate = datePicker.getValue(); 
		if(tempDate==null){
			
		}
		else{


			LocalDate dateSelected;
			dateSelected = datePicker.getValue();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			dateStamp = dateSelected.format(formatter);

		}

		ArrayList<String> results;

		switch(searchCriteria1){

		case "orderId":
			int orderId = Integer.valueOf(searchCriteria2);
			results = searchManager.orderIdSearch(orderId);
			
			
			if(results.contains(dateStamp)){
				
				//removing all commas
				while(results.contains(",")){
					results.remove(",");
		
				}


				while(results.contains(dateStamp)){

					int index = results.indexOf(dateStamp);
					
					//reaching last element in row
					int counter = index+1;
					

					//dateStamp column index = 7
					//setting pointer
					int updatedIndex =index -7;

					while(counter >= updatedIndex){

					
						String str = results.get(updatedIndex);
						searchTextArea.appendText(str+" || ");
						updatedIndex++;

					}
					results.remove(index);
					searchTextArea.appendText("\n");

				}




			}else if(dateStamp.equals("empty")){
				for(String str : results){
					if(str.equals(",")){
						searchTextArea.appendText("\n");
						continue;
					}

					searchTextArea.appendText(str+" || ");

				}

			}else { searchTextArea.clear();}
			break;

		case "Table Number":
			int tableNumber= Integer.valueOf(searchCriteria2);
			results = searchManager.tableNumSearch(tableNumber);


			if(results.contains(dateStamp)){
				
				//removing all commas
				while(results.contains(",")){
					results.remove(",");
					
				}


				while(results.contains(dateStamp)){

					int index = results.indexOf(dateStamp);

					//reaching last element in row
					int counter = index+1;
					
					//dateStamp column index = 7
					//setting pointer
					int updatedIndex =index -7;

					while(counter >= updatedIndex){

						String str = results.get(updatedIndex);
						searchTextArea.appendText(str+" || ");
						updatedIndex++;

					}
					results.remove(index);
					searchTextArea.appendText("\n");

				}




			}else if(dateStamp.equals("empty")){
				for(String str : results){
					if(str.equals(",")){
						searchTextArea.appendText("\n");
						continue;
					}

					searchTextArea.appendText(str+" || ");

				}

			}else { searchTextArea.clear();}
			break;

		case "FoodItem":
			String foodItem = searchCriteria2;
			results = searchManager.foodItemDescriptionSearch(foodItem);



			if(results.contains(dateStamp)){
				

				//removing all commas
				while(results.contains(",")){
					results.remove(",");
					
				}


				while(results.contains(dateStamp)){

					int index = results.indexOf(dateStamp);

					//reaching last element in row
					int counter = index+1;
					

					//dateStamp column index = 7
					//setting pointer
					int updatedIndex =index -7;

					while(counter >= updatedIndex){

						String str = results.get(updatedIndex);
						searchTextArea.appendText(str+" || ");
						updatedIndex++;

					}
					results.remove(index);
					searchTextArea.appendText("\n");

				}




			}else if(dateStamp.equals("empty")){
				for(String str : results){
					if(str.equals(",")){
						searchTextArea.appendText("\n");
						continue;
					}

					searchTextArea.appendText(str+" || ");

				}

			}else { searchTextArea.clear();}
			break;

		case "empId":
			int empId = Integer.valueOf(searchCriteria2);
			results = searchManager.empIdSearch(empId);


			if(results.contains(dateStamp)){
				

				//removing all commas
				while(results.contains(",")){
					results.remove(",");
					
				}


				while(results.contains(dateStamp)){

					int index = results.indexOf(dateStamp);


					
					//reaching last element in row
					int counter = index+1;
					

					//dateStamp column index = 7
					//settign pointer
					int updatedIndex =index -7;

					while(counter >= updatedIndex){

						String str = results.get(updatedIndex);
						searchTextArea.appendText(str+" || ");
						updatedIndex++;

					}
					results.remove(index);
					searchTextArea.appendText("\n");

				}



			}else if(dateStamp.equals("empty")){
				for(String str : results){
					if(str.equals(",")){
						searchTextArea.appendText("\n");
						continue;
					}

					searchTextArea.appendText(str+" || ");

				}

			}else { searchTextArea.clear();}
			break;


		}



		if(searchTextArea.getText().equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			
			
			
			Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			alert.setTitle("Restaurant Billing System");
			alert.setHeaderText("Order Search Status: No Match Found");
			alert.setContentText("Your search returned no results");

			alert.showAndWait();
		}

	}
	
	/**
	 * 
	 * <p>Takes the user from the current screen back to the OrderHistory screen, opening the
	 * OrderHistory.fxml file.</p>
	 * 
	 * 
	 * @param event - pressing the back button.
	 */
	@FXML
	private void goBack(ActionEvent event){
		Stage stageToClose = (Stage) backBtn.getScene().getWindow();
	    stageToClose.close();
    	try{
	
			Parent root1 = FXMLLoader.load(getClass().getResource("OrderHistory.fxml"));
	     
	        Stage stage = new Stage();
	        Scene scene = new Scene(root1);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
			
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
	        stage.setTitle("Restaurant Billing System - Orders History");
	        stage.setScene(scene);
	        stage.show();
	            
	        
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	}
	
}
