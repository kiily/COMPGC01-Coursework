package uk.ac.ucl.cs.gc01.restaurant_billing_system.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.OrderManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.StaffManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.TableManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.OrderDetails;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.OrderItems;

/**
 * 
 * 
 * <p>The OrderHistoryController class provides the methods and elements of the GUI so 
 * that the user can act upon the elements defined in the FXML file for the orders history screen
 * (OrdersHistory.fxml).
 * Accessibility to the FXML file is achieved with the following tag: @FXML. </p>
*	
 * <p> Note that this class should be added as a controller to SceneBuilder and all the
 * elements should have their fx:id specified in the code section. This applies to all other controllers.</p> 
 * 
 * <p>The class automatically initializes the data in the orderDetailsDataTable TableView from the database.</p>
 * 
 * <p>It provides the following methods:</p>
 * <ul>
 * 		<li><b>initialize(URL, ResourceBundle)</b>Initializes the GUI.</li>
 * 		<li> <b>deleteOrder(ActionEvent)</b> - triggered by pressing the delete order button. Removes selected menu item from the database; asks
 * for confirmation prior to removal.</li>
 * 		<li> <b>goToAdminHome(ActionEvent)</b> - triggered by pressing the Admin Home button. Sends user back to AdminHome screen.</li>
 * 		<li> <b>goToEmployeeHome(ActionEvent)</b> - triggered by pressing the Employee Home button. Sends user back to EmployeeHome screen.</li>
 * 		<li> <b>goToSearchOrder(ActionEvent)</b> - triggered by pressing the Search Order button. Sends user to Search Orders screen.</li>
 * 		<li> <b>refreshTable(ActionEvent)</b> - triggered by pressing the refresh button. Refreshes the orderDetailsDataTable TableView. </li>
 * 		<li> <b>editOrderId(ActionEvent)</b> - triggered by pressing the edit order button. Sends the user to the Orders screen. </li>
 * 		<li> <b>exportOrder(ActionEvent)</b> - triggered by pressing the export order button. Exports selected order as a .csv file. </li>
 * 		<li> <b>importOrder(ActionEvent)</b> - triggered by pressing the import order button. Opens a file chooser and stores an order specified as csv.</li>
 * </ul>	
 * 
 * <b>References:</b>
 *
 * <ul>
 * <li>https://www.youtube.com/watch?v=hNz8Xf4tMI4&#38;index=16&#38;list=PLS1QulWo1RIaUGP446_pWLgTZPiFizEMq;</li>
 * <li>http://code.makery.ch/blog/javafx-dialogs-official/</li>
 * <li>http://stackoverflow.com/questions/21534515/jfilechooser-open-in-current-directory</li>
 * <li>https://github.com/yinyee/coursework/tree/master/data</li>
 * <li>http://www.java-tips.org/other-api-tips-100035/69-jdbc/911-how-to-export-data-from-database-to-csv-file.html</li>
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class OrderHistoryController implements Initializable {
	
	@FXML
	private Tab orderManagementTab;
	@FXML
	private Tab importExportTab;
	@FXML
	private TextArea textArea;
	@FXML
	private Button chooseFileBtn;
	@FXML
	private Button exportOrderBtn;
	@FXML
	private Button adminHomeBtn;
	@FXML
	private Button employeeHomeBtn;
	@FXML
	private Button refreshTableBtn;
	@FXML
	private Button orderSearchBtn;
	@FXML
	private Button editOrderBtn;
	@FXML 
	private Button deleteOrderBtn;
	@FXML
	private TableView<OrderDetails> orderDetailsDataTable;
	@FXML
	private TableColumn<OrderDetails, Integer> orderIdColumn;
	@FXML
	private TableColumn<OrderDetails, Integer> tableNumberColumn;
	@FXML
	private TableColumn<OrderDetails, String> orderTimeColumn;
	@FXML
	private TableColumn<OrderDetails, String> orderDateColumn;
	@FXML
	private TableColumn<OrderDetails, Integer> empIdColumn;
	
	
	private ObservableList<OrderDetails> orderDetailsData;
	

	//database manipulators
	private OrderManager orderManager = new OrderManager();
	private StaffManager staffManager = new StaffManager();
	private TableManager tableManager = new TableManager();
	
	/**
	 * <p>Initializes the GUI and TableView.</p>
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		String type="";
		try {
			type = staffManager.getStaffType();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if(type.equalsIgnoreCase("Employee")){
			importExportTab.setDisable(true);
			adminHomeBtn.setDisable(true);
		}else if(type.equalsIgnoreCase("Manager")){
			employeeHomeBtn.setDisable(true);
		}
		
		try {
			orderDetailsData = orderManager.buildOrderDetailsData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//initializing TableView
		orderIdColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails, Integer>("orderId"));
		tableNumberColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails, Integer>("tableNumber"));
		orderTimeColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails, String>("orderTime"));
		orderDateColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails, String>("orderDate"));
		empIdColumn.setCellValueFactory(new PropertyValueFactory<OrderDetails, Integer>("empId"));
		
		orderDetailsDataTable.setItems(orderDetailsData);
		
	}
	
	
	

	/**
	 * <p> Removes the order selected in the orderDetailsDataTable TableView 
	 * and deletes it from the database. A dialog will be displayed to allow the user to confirm
	 * that they want to delete the order. If no order is selected from the TableView an Alert is displayed.</p>
	 * 
	 * @param event - pressing the delete order button.
	 * @throws SQLException - SQL handling exception.
	 */
	@FXML
	private void deleteOrder(ActionEvent event) throws SQLException {
		

		if(orderDetailsDataTable.getSelectionModel().getSelectedItem() ==null){
			Alert alert = new Alert(AlertType.WARNING);

			Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			alert.setTitle("Restaurant Billing System - WARNING");
			alert.setHeaderText("No order Selected");
			alert.setContentText("Please select an order to remove.");

			alert.showAndWait();
		}

		OrderDetails orderDetailsToRemove = orderDetailsDataTable.getSelectionModel().getSelectedItem();
		int selectedIndex  = orderDetailsToRemove.getOrderId();
		

		Alert alert = new Alert(AlertType.CONFIRMATION);
		Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
		tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
		alert.setTitle("Restaurant Billing System - Confirmation Dialog");
		alert.setHeaderText("You are about to delete an Order");
		alert.setContentText("Are you ok with this? - orderID: "+selectedIndex+" will be deleted");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			orderManager.destroyOrderRecord(selectedIndex);	

			orderDetailsData = orderManager.buildOrderDetailsData();
			orderDetailsDataTable.setItems(orderDetailsData);
			alert.close();


		}

	}

	/**
	 *  
	 * <p>Takes the user (if admin) from the current screen back to the admin home screen, opening the
	 * AdminHome.fxml file.</p>
	 * 
	 * @param click - pressing the Admin Home button.
	 */
	@FXML
	private void goToAdminHome(ActionEvent click){
		
		Stage stageToClose = (Stage) adminHomeBtn.getScene().getWindow();
	    stageToClose.close();
    	try{
			
			Parent root1 = FXMLLoader.load(getClass().getResource("AdminHome.fxml"));
	     
	        Stage stage = new Stage();
	        Scene scene = new Scene(root1);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	       
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
	        stage.setTitle("Restaurant Billing System - Admin Home");
	        stage.setScene(scene);
	        stage.show();
	        
	     
	        
	        
	        
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	}
	
	
	/**
	 *  
	 * <p>Takes the user (if not a Manager) from the current screen back to the employee home screen, opening the
	 * EmployeeHome.fxml file.</p>
	 * 
	 * @param click - pressing the employee button.
	 * @throws SQLException - SQL handling exception.
	 * @throws IOException - I/O handling error.
	 * 
	 */ 
	@FXML
	private void goToEmployeeHome(ActionEvent click) throws SQLException, IOException{
		Stage stageToClose = (Stage) employeeHomeBtn.getScene().getWindow();
        stageToClose.close();
        
        int empID = staffManager.retrieveConnectedEmpId(LoginController.username, LoginController.password);
	try{
		FXMLLoader loader2 = new FXMLLoader();
				
        Parent root = loader2.load(getClass().getResource("EmployeeHome.fxml").openStream());
        EmployeeHomeController employeeHomeController = (EmployeeHomeController) loader2.getController();
        
        
        employeeHomeController.setEmpID(empID);
        employeeHomeController.setEmployeeName(empID);
        
        
     
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      
		
		stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
        stage.setTitle("Restaurant Billing System - Employee Home Menu");
        stage.setScene(scene);
        stage.show();
        
      
        
        
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
	}
	
	/**
	 * <p>Takes the user from the current screen to the Orders Search screen, opening the
	 * EmployeeHome.fxml file.</p>
	 * 
	 * @param click - pressing the search order button.
	 * @throws SQLException - SQL handling exception.
	 */
	@FXML
	private void goToSearchOrder(ActionEvent click) throws SQLException{
		  Stage stageToClose = (Stage) orderSearchBtn.getScene().getWindow();
	        stageToClose.close();
	        
	    
  	try{
  			
			
	        Parent root = FXMLLoader.load(getClass().getResource("OrderSearch.fxml"));
	        
	     
	        Stage stage = new Stage();
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
	        stage.setTitle("Restaurant Billing System - Orders Search");
	        stage.setScene(scene);
	        stage.show();
	        
	      
	        
	        
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	}
	
	
	/**
	 * * 
	 * <p>Refreshes the orderDetailsDataTable TableView.</p>
	 * 
	 * @param click - pressing the refresh table button.
	 * @throws SQLException - SQL handling exception.
	 */
	@FXML
	private void refreshTable(ActionEvent click) throws SQLException{
		
		orderDetailsData = orderManager.buildOrderDetailsData();
		orderDetailsDataTable.setItems(orderDetailsData);
	}
	
	/**
	 * 
	 * <p> Takes the order selected in the orderDetailsDataTable and loads
	 * an alternative view of the Orders.fxml GUI where the Add Order button is disabled and the
	 * cancel order button is replaced by a finish button. If no order is selected an Alert is displayed.
	 * </p>
	 * 
	 * @param event - pressing the edit order button.
	 * @throws SQLException - SQL handling exception.
	 */
	@FXML
	private void editOrderId(ActionEvent event) throws SQLException{
		
		
	if(orderDetailsDataTable.getSelectionModel().getSelectedItem() ==null){
		Alert alert = new Alert(AlertType.WARNING);
        
		Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
		tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
        alert.setTitle("Restaurant Billing System - WARNING");
        alert.setHeaderText("No order Selected");
        alert.setContentText("Please select an order to edit.");

        alert.showAndWait();
		}
		
		OrderDetails orderDetailsToChange = orderDetailsDataTable.getSelectionModel().getSelectedItem();
		int selectedIndex  = orderDetailsToChange.getOrderId();
		int tableNumber = orderDetailsToChange.getTableNumber();
		
		
		try{
		FXMLLoader loader2 = new FXMLLoader();
		
		Parent root = loader2.load(getClass().getResource("Orders.fxml").openStream());
	    OrdersController ordersNewController = (OrdersController) loader2.getController();
	    
	    ordersNewController.setOrderId(selectedIndex);
	    ordersNewController.setTableNum(Integer.toString(tableNumber));
	    
	    ordersNewController.setRemItemChoice(orderManager.getItemDescriptionToRemove(selectedIndex));
		ordersNewController.setRemItemDescription(ordersNewController.getRemItemChoice());
				
		
		Button closeOrderBtn = ordersNewController.getCloseOrderBtn();
		closeOrderBtn.setDisable(true);
		ordersNewController.setCloseOrderBtn(closeOrderBtn);
		
		Button cancelOrderBtn = ordersNewController.getCancelOrderBtn();
		cancelOrderBtn.setDisable(true);
		ordersNewController.setCancelOrderBtn(cancelOrderBtn);
		
		
		Button registerOrderBtn = ordersNewController.getRegisterOrderBtn();
	    registerOrderBtn.setDisable(true);
	    ordersNewController.setRegisterOrderBtn(registerOrderBtn);
	    
	    Button backBtn = ordersNewController.getBackBtn();
	    backBtn.setVisible(false);
	    ordersNewController.setBackBtn(backBtn);
		
	    ObservableList<OrderItems> orderItemsData = ordersNewController.getOrderItemsData();
	    
		orderItemsData= orderManager.buildOrderItemsData(selectedIndex);
		ordersNewController.setOrderTable(orderItemsData);
		
			   
		Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
       
		
		
		stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
        stage.setTitle("Restaurant Billing System - Employee Home Menu");
        stage.setScene(scene);
        stage.show();  
        
    	Stage stageToClose = (Stage) editOrderBtn.getScene().getWindow();
		stageToClose.close();
		
    	}catch(IOException ex){
    		ex.printStackTrace();
    	}
		
	}
	
	/**
	 *
	 * <p> Takes the order selected in the orderDetailsDataTable and exports it
	 * as a .csv file to the project folder. If the operation is successful, a notification is displayed.
	 * If no order is selected an Alert is displayed.</p>
	 * 
	 * @param event - pressing the export order button.
	 * @throws SQLException - SQL handling exception .
	 * @throws IOException - I/O error occurs.
	 */
	@FXML
	private void exportOrder(ActionEvent event) throws SQLException, IOException{
		
		if(orderDetailsDataTable.getSelectionModel().getSelectedItem() ==null){
			Alert alert = new Alert(AlertType.WARNING);
	    
			
			Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
	        alert.setTitle("Restaurant Billing System - WARNING");
	        alert.setHeaderText("No order Selected");
	        alert.setContentText("Please select an order to edit.");

	        alert.showAndWait();
			}
			
			OrderDetails orderDetailsToChange = orderDetailsDataTable.getSelectionModel().getSelectedItem();
			int selectedIndex  = orderDetailsToChange.getOrderId();
			
			ArrayList<String> export = orderManager.exportData(selectedIndex);
			
			BufferedWriter br = new BufferedWriter(new FileWriter("myOrder.csv"));
			
			StringBuilder sb = new StringBuilder();
			
			for (String element : export) {
			
			if(element.equals(",")){
				sb.append("\n");
				continue;
			}
			 sb.append(element);
			 sb.append(",");
			}

			br.write(sb.toString());
			br.close();
			
			
			Alert alert = new Alert(AlertType.INFORMATION);
	        
		
			
			Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
	        alert.setTitle("Restaurant Billing System");
	        alert.setHeaderText("Order Export Status:");
	        alert.setContentText("Order has been successfully exported");

	        alert.showAndWait();
	}
	
	/**
	 * 
	 * <p> Opens a file chooser allowing the user to select a .csv file
	 * from the project folder. This csv file should contain an order in csv format. </p>
	 * 
	 * 
	 * <p> Example of a valid order format: foodItemDescription, specialRequests, comments, quantity.</p>
	 * 
	 * <p>After being imported, the order is automatically added to the database.</p>
	 * 
	 * @param event - pressing the import order button.
	 * @throws SQLException - SQL handling exception.
	 * @throws MalformedURLException - - URL for icon is malformed.
	 */
	@FXML
	private void importOrder(ActionEvent event) throws SQLException, MalformedURLException{
		
		FileChooser fc = new FileChooser();

		
		fc.setInitialDirectory(new File(System.getProperty("user.dir")));
		fc.getExtensionFilters().addAll(new ExtensionFilter("CSV FILES", "*.csv"));
		File selectedFile = fc.showOpenDialog(null);
		
		
		
		BufferedReader br = null;
		StringBuffer sB= new StringBuffer("");

		String line ="";

		int tableNum =0;
		int currentEmpId = LoginController.empId;
		
		if(selectedFile.toURI().toURL().toString()!= null){

		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Restaurant Billing System - Table Number Selection");
		dialog.setHeaderText("Please enter the table to which you want to add the order");
		dialog.setContentText("Table Number:");
		
		
		
		Stage tempStage = (Stage) dialog.getDialogPane().getScene().getWindow();
		tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			tableNum = Integer.valueOf(result.get());
		
			
			if(tableManager.isTableNumber(tableNum)== true){


			OrderDetails order = orderManager.generateOrderRecord(tableNum, currentEmpId);
			int orderId = orderManager.getOrderId(order,tableNum);

			try{

				br = new BufferedReader(new FileReader(selectedFile));

				while((line = br.readLine())!=null){

					sB.append(line+",");
					textArea.appendText(line+"\n");

					String[] orderElements = new String[4];
					orderElements =  line.split(",");

					String foodItemDescription= orderElements[0];

					String specialRequests= orderElements[1];
					String comments= orderElements[2];
					int quantity = Integer.valueOf(orderElements[3]);

					orderManager.addOrderItems(foodItemDescription, quantity, orderId, comments, specialRequests);
				}

			}catch(IOException e ){
				e.printStackTrace();
			}
			
			}else{
				Alert alert = new Alert(AlertType.ERROR);
		        
				
				Stage tempStage1 = (Stage) alert.getDialogPane().getScene().getWindow();
				tempStage1.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
		        alert.setTitle("Restaurant Billing System - ERROR");
		        alert.setHeaderText("Table Number does not exist");
		        alert.setContentText("Please enter a valid table number: 1-7");

		        alert.showAndWait();
			}
			
			
		}else{
			dialog.close();
		}
		
		}

	}
	
	

}
	

