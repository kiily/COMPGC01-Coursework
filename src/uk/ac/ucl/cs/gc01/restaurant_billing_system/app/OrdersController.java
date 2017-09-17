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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.OrderManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.TableManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.OrderItems;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.Table;

/**
 * 
 * 
 * <p>The OrdersController class provides the methods and elements of the GUI so 
 * that the user can act upon the elements defined in the FXML file for the orders screen
 * (Orders.fxml).
 * Accessibility to the FXML file is achieved with the following tag: @FXML. </p>
*	
 * <p> Note that this class should be added as a controller to SceneBuilder and all the
 * elements should have their fx:id specified in the code section. This applies to all other controllers.</p> 
 * 
 * 
 * <p>It provides the following methods:</p>
 * <ul>
 * 		<li><b>initialize(URL, ResourceBundle)</b>Initializes the GUI.</li>
 * 		<li> <b>addToOrder(ActionEvent)</b> - triggered by pressing the add to order button. Adds specified FoodItem to order and database.</li>
 * 		<li> <b>removeFromOrder(ActionEvent)</b> - triggered by pressing the remove from order button. Removes specified FoodItem from the order and the database.</li>
 * 		<li> <b>registerOrder(ActionEvent)</b> - triggered by pressing the register order button. Registers the order and sends user back to tables screen. </li>
 * 		<li> <b>closeOrder(ActionEvent)</b> - triggered by pressing the close order button. Sends user back to the Tables screen and changes
 *  the status of the current table to free.</li>
 *  	<li> <b>cancelOrder(ActionEvent)</b> - triggered by pressing the cancel order button. Cancels order and sends user back to Tables screen.</li>
 * 		<li> <b>computerOrderTotal(ActionEvent)</b> - triggered by pressing the calculate total button. Calculates the total price of the order.</li>
 * 		<li> <b>editOrderId(ActionEvent)</b> - triggered by pressing the edit order button. Sends the user to the Orders screen. </li>
 * 		<li> <b>finishEdit(ActionEvent)</b> - triggered by pressing the finish button. Finishes order editing; sends user to Order History screen. </li>
 * 		<li> <b>goBack(ActionEvent)</b> - triggered by pressing the back button. Sends user back to the Tables screen.</li>
 *
 * </ul>	
 * 
 * <b>References:</b>
 * <ul>
 *  <li>http://code.makery.ch/blog/javafx-dialogs-official/</li>
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class OrdersController implements Initializable {
	
	@FXML
	private Button backBtn;
	@FXML
	private Button cancelOrderBtn;
	@FXML
	private Button registerOrderBtn;
	@FXML
	private Button finishEditBtn;
	@FXML
	private Button closeOrderBtn;
	@FXML
	private Button addItem;
	@FXML
	private Button removeItem;
	@FXML
	private Label tableNumberLabel;
	@FXML
	private Label orderIdLabel;
	@FXML
	private Label numberOfPeopleLabel;
	@FXML
	private Label totalLabel;
	@FXML
	private TextField orderIdTextField;
	@FXML
	private TableView<OrderItems> orderTable;
	@FXML
	private TableColumn<OrderItems, String> foodItemColumn;
	@FXML
	private TableColumn<OrderItems, Integer> quantityColumn;
	@FXML
	private TableColumn<OrderItems, Double> rateColumn;
	@FXML
	private TableColumn<OrderItems, String> specialRequestsColumn;
	@FXML
	private TableColumn<OrderItems, String> commentsColumn;
	@FXML
	private ChoiceBox<String> addItemDescription;
	@FXML
	private ChoiceBox<String> remItemDescription;
	@FXML
	private TextArea comments;
	@FXML
	private TextArea specialRequests;
	@FXML
	private Spinner<Integer> addQuantity;
	@FXML
	private Spinner<Integer> remQuantity;
	
	
	private ObservableList<OrderItems> orderItemsData;
	
	private ObservableList<String> addItemChoice;
	private ObservableList<String> remItemChoice;
	
	
	//database manipulators
	private OrderManager orderManager = new OrderManager();
	private TableManager tableManager = new TableManager();	
			
	
	/**
	 * <p>Initializes the GUI, Spinners and choice boxes</p>		
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
        SpinnerValueFactory<Integer> valueFactoryQuantityAdd = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        SpinnerValueFactory<Integer> valueFactoryQuantityRemove = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        
        
        addQuantity.setValueFactory(valueFactoryQuantityAdd);
        remQuantity.setValueFactory(valueFactoryQuantityRemove);
		
        try {
			addItemChoice = orderManager.getItemDescriptionToAdd();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        
		addItemDescription.setItems(addItemChoice);
		addItemDescription.setTooltip(new Tooltip("Select the item to add"));
		
		
		//initializing the TableView
		foodItemColumn.setCellValueFactory(new PropertyValueFactory<OrderItems, String>("foodItem"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItems, Integer>("quantity"));
		rateColumn.setCellValueFactory(new PropertyValueFactory<OrderItems, Double>("rate"));
		specialRequestsColumn.setCellValueFactory(new PropertyValueFactory<OrderItems, String>("specialRequests"));
		commentsColumn.setCellValueFactory(new PropertyValueFactory<OrderItems, String>("comments"));
		
		
	}
	
	
	
	/**
	 * <p> Adds the specified element to the order and updates the database. It inserts
	 * the FoodItem (quantity can be set in the adjacent Spinner) into the database and updates the orderTable TableView. An alert is displayed
	 * if the item is added successfully. </p>
	 * 
	 * @param  event - pressing the add order button.
	 * @throws SQLException - SQL handling exception.
	 * @throws MalformedURLException - URL for icon is malformed.
	 */
	@FXML
	private void addToOrder(ActionEvent event) throws SQLException, MalformedURLException{
		
		String comment = comments.getText();
		String specialRequest = specialRequests.getText();

		String orderNumStr = orderIdTextField.getText();
		int orderNum = Integer.valueOf(orderNumStr);

		int quantity = addQuantity.getValue();

		String foodItemToAdd= addItemDescription.getSelectionModel().selectedItemProperty().get();

		if(foodItemToAdd != null){
			orderManager= new OrderManager();

			try{


				orderManager.addOrderItems(foodItemToAdd, quantity, orderNum, comment, specialRequest);

				orderItemsData = orderManager.buildOrderItemsData(Integer.valueOf(orderIdTextField.getText()));
				orderTable.setItems(orderItemsData);

				//clear fields after use
				comments.clear();
				specialRequests.clear();


			}catch(SQLException e){
				e.printStackTrace();


			}finally{
				setRemItemChoice(orderManager.getItemDescriptionToRemove(orderNum));
				remItemDescription.setItems(getRemItemChoice());
				remItemDescription.setTooltip(new Tooltip("Select the item to remove"));
			}

		}else{
			Alert alert1 = new Alert(AlertType.ERROR);
			

			Stage tempStage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
			tempStage1.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			alert1.setTitle("Restaurant Billing System - Error");
			alert1.setHeaderText("No item to add was selected");
			alert1.setContentText("Please select a valid item!");

			alert1.showAndWait();
		}

	}
	
	/**
	 * <p>Removes the specified element from the order and updates the database. It deletes
	 * the FoodItem (quantity can be set in the adjacent Spinner) from the database and updates the orderTable TableView. An alert is displayed
	 * if the item is removed successfully. </p>
	 * 
	 * @param event - pressing the remove from order button.
	 * @throws SQLException - SQL handling exception.
	 * @throws MalformedURLException - URL for icon is malformed.
	 */
	@FXML
	private void removeFromOrder(ActionEvent event) throws SQLException, MalformedURLException{
		
		
		int orderNum = Integer.valueOf(orderIdTextField.getText());
		int quantity =  remQuantity.getValue();
		orderManager= new OrderManager();
		
		
		String foodItemToremove= remItemDescription.getSelectionModel().selectedItemProperty().get();
		orderManager.removeOrderItems(foodItemToremove, quantity, orderNum);
		
		
		orderItemsData = orderManager.buildOrderItemsData(Integer.valueOf(orderIdTextField.getText()));
		orderTable.setItems(orderItemsData);
		
		
		setRemItemChoice(orderManager.getItemDescriptionToRemove(orderNum));
		remItemDescription.setItems(getRemItemChoice());
		remItemDescription.setTooltip(new Tooltip("Select the item to remove"));
		
		if(foodItemToremove != null){
		Alert alert = new Alert(AlertType.INFORMATION);
			
		Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
		
		alert.setHeight(20);
		tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
		alert.setTitle("Restaurant Billing System");
		alert.setHeaderText("Operation Status");
		alert.setContentText("Item removed Successfully!");

		alert.showAndWait();
		
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			
			
			Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			alert.setTitle("Restaurant Billing System - Error");
			alert.setHeaderText("No item to remove was selected");
			alert.setContentText("Please select a valid item!");

			alert.showAndWait();
		}
		
	}
	
	/**
	 * <p>Registers the currently open order in the database and sends
	 * the user back to the Tables.fxml screen. It changes the table status to busy (table becomes
	 * red) and it updates the number of people in the capacity label.</p>
	 *  
	 * @param event - pressing the register order button.
	 * @throws SQLException - SQL handling exception.
	 */
	@FXML
	private void registerOrder(ActionEvent event) throws SQLException{
		
		int empId = TablesController.empId;
		
		String tableNumStr = tableNumberLabel.getText();

		int tableNum = Integer.valueOf(tableNumStr.substring(14));

		boolean currentStatus = tableManager.getTableStatus(tableNum);
		//change the boolean here upon registering an order 
		boolean newStatus = !currentStatus;


		String numOfPeopleStr = numberOfPeopleLabel.getText();

		int numOfPeople = Integer.valueOf(numOfPeopleStr.substring(18));

		//another object table, now with occupied
		Table table = new Table(tableNum, newStatus, numOfPeople);

		tableManager.updateTableRecord(table);



		try{

			FXMLLoader loader2 = new FXMLLoader();
			Parent root1 = loader2.load(getClass().getResource("Tables.fxml").openStream());

			TablesController tablesController = (TablesController) loader2.getController();

			tablesController.disableBtn(TablesController.getStaffType());
			tablesController.setEmpId(empId);
			tablesController.changeToBusy(table);
			tablesController.updateNumberOfPeople(table, numOfPeople);


			Stage stage = new Stage();
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			stage.setTitle("Restaurant Billing System - Tables");
			stage.setScene(scene);
			stage.show();

			Stage stageToClose = (Stage) registerOrderBtn.getScene().getWindow();
			stageToClose.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * <b>Description</b>
	 * 
	 * <p>This method closes the current order and sends the user to the Tables screen. The table
	 * at which the order was created will be filled in green as the method changes the 
	 * table status to free. Closing an order corresponds to people leaving the restaurant</p>
	 * 
	 * @param event - pressing the close order button
	 * @throws SQLException - SQL handling exception
	 */
	@FXML
	private void closeOrder(ActionEvent event) throws SQLException{
		
		
		
		String tableNumStr = tableNumberLabel.getText();
		
		int tableNum = Integer.valueOf(tableNumStr.substring(14));
	
		boolean currentStatus = tableManager.getTableStatus(tableNum);
		
		boolean newStatus = !currentStatus;
		//change the boolean here upon registering an order 
		
		String numOfPeopleStr = numberOfPeopleLabel.getText();
		
		int numOfPeople = Integer.valueOf(numOfPeopleStr.substring(18));
			
		//another object table,now with occupied status
		Table table = new Table(tableNum, newStatus, numOfPeople);
		
		tableManager.updateTableRecord(table);
		
		//order is closed when people leave the table
		table.setNumberOfPeople(0);
		tableManager.updateNumberOfPeople(table);
		
		
		try{
			
			FXMLLoader loader2 = new FXMLLoader();
			Parent root1 = loader2.load(getClass().getResource("Tables.fxml").openStream());
	     
			TablesController tablesController = (TablesController) loader2.getController();
			
			tablesController.disableBtn(TablesController.getStaffType());
			tablesController.setEmpId(LoginController.empId);
			
			
					
	        Stage stage = new Stage();
	        Scene scene = new Scene(root1);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	       
			
			Alert alert = new Alert(AlertType.INFORMATION);
			Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			
			alert.setTitle("Restaurant Billing System");
			alert.setHeaderText("Order Status: CLOSED");
			alert.setContentText("The order was closed!");
			alert.showAndWait();
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
	        stage.setTitle("Restaurant Billing System - Tables");
	        stage.setScene(scene);
	        stage.show();
	        
	        Stage stageToClose = (Stage) closeOrderBtn.getScene().getWindow();
			stageToClose.close();
			
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	}
	
	/**
	 * <b>Description:</b>
	 * 
	 * <p> The cancelOrder method cancels the current order and returns the user
	 * to the Tables.fxml screen. It updates the table label of the currently
	 * selected table. If an IOException is caught, an Alert is displayed</p>
	 * 
	 * 
	 * @param event - pressing the cancel order button
	 * @throws SQLException - SQL handling exception
	 * @throws MalformedURLException - URL for icon is malformed
	 */
	@FXML
	private void cancelOrder(ActionEvent event) throws SQLException, MalformedURLException{
		
		int empId = LoginController.empId;
		int orderId = Integer.valueOf(orderIdTextField.getText());
		
		try{
			
			FXMLLoader loader2 = new FXMLLoader();
			Parent root1 = loader2.load(getClass().getResource("Tables.fxml").openStream());

			TablesController tablesController = (TablesController) loader2.getController();

			tablesController.disableBtn(TablesController.getStaffType());
			tablesController.setEmpId(empId);
			
			Stage stage = new Stage();
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			orderManager.destroyOrderRecord(orderId);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			
			alert.setTitle("Restaurant Billing System");
			alert.setHeaderText("Order Status: CANCELLED");
			alert.setContentText("The order was cancelled!");
			alert.showAndWait();
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			stage.setTitle("Restaurant Billing System - Tables");
			stage.setScene(scene);
			stage.show();
			
			

			Stage stageToClose = (Stage) cancelOrderBtn.getScene().getWindow();
			stageToClose.close();

		

		}catch(IOException e){
			Alert alert1 = new Alert(AlertType.ERROR);
			
			Stage tempStage = (Stage) alert1.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			
			alert1.setTitle("Restaurant Billing System - Error");
			alert1.setHeaderText("Operation Failed");
			alert1.setContentText("Please try again!");

			alert1.showAndWait();
			e.printStackTrace();
		}
			
		
	}
	
	/**
	 * <b>Description:</b>
	 * 
	 * <p>The computeOrderTotal method computes the total price of the order and updates 
	 * the totalLabel accordingly. Total is in £</p>
	 * 
	 * @param event - pressing the calculate order button
	 * @throws SQLException - SQL handling exception
	 */
	@FXML
	private void computeOrderTotal(ActionEvent event) throws SQLException{
		
		int orderId = Integer.valueOf(orderIdTextField.getText());
		double total = orderManager.calculateOrderTotal(orderId);
		
		totalLabel.setText("TOTAL: "+Double.toString(total)+"£");
	}
	
	
	
	
	/**
	 * <b>Description:</b>
	 * 
	 * <p>The finishEdit method finsihes the process of editing an order and sends
	 * the user to the Order History screen</p>
	 * 
	 * @param event - pressing the finish button.
	 */
	@FXML
	private void finishEdit(ActionEvent event){
		
		try{
		
			
	        Parent root =  FXMLLoader.load(getClass().getResource("OrderHistory.fxml"));
	       
	     
	        Stage stage = new Stage();
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
			
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
	        stage.setTitle("Order History");
	        stage.setScene(scene);
	        stage.show();
	        
	        Stage stageToClose = (Stage) finishEditBtn.getScene().getWindow();
			stageToClose.close();
	        
	        
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
		
	}
	
	/**
	 * <b>Description</b>
	 * 
	 * <p>This method takes the user from the current screen back to the Tables screen, opening the
	 * Tables.fxml file</p>
	 * 
	 * @param event - pressing the back button
	 * @throws SQLException - SQL handling exception
	 */
	@FXML
	private void goBack(ActionEvent event) throws SQLException{
		
		//irrelevant here
		boolean status = true;
		
		String tableNumStr = tableNumberLabel.getText();
		int tableNum = Integer.valueOf(tableNumStr.substring(14));
		String numOfPeopleStr = numberOfPeopleLabel.getText();
		
		int numOfPeople = Integer.valueOf(numOfPeopleStr.substring(18));
		
				
		Table table = new Table(tableNum, status, numOfPeople);
		
		try{
			
			FXMLLoader loader2 = new FXMLLoader();
			Parent root1 = loader2.load(getClass().getResource("Tables.fxml").openStream());
	     
			TablesController tablesController = (TablesController) loader2.getController();
			
			tablesController.disableBtn(TablesController.getStaffType());
			tablesController.setEmpId(LoginController.empId);
			
			tablesController.updateNumberOfPeople(table, numOfPeople);
			

			
	        Stage stage = new Stage();
	        Scene scene = new Scene(root1);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	      
			
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
	        stage.setTitle("Restaurant Billing System - Tables");
	        stage.setScene(scene);
	        stage.show();
	        
	        
	    	Stage stageToClose = (Stage) backBtn.getScene().getWindow();
			stageToClose.close();
			
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	}
	
	
	
		
	
	
	//getters and setters for the GUI elements
	
	public Button getBackBtn() {
		return backBtn;
	}


	public void setBackBtn(Button backBtn) {
		this.backBtn = backBtn;
	}
	
	public Button getCancelOrderBtn() {
		return cancelOrderBtn;
	}


	public void setCancelOrderBtn(Button cancelOrderBtn) {
		this.cancelOrderBtn = cancelOrderBtn;
	}


	public Button getRegisterOrderBtn() {
		return registerOrderBtn;
	}


	public void setRegisterOrderBtn(Button registerOrderBtn) {
		this.registerOrderBtn = registerOrderBtn;
	}	
	
	public Button getFinishEditBtn() {
		return finishEditBtn;
	}


	public void setFinishEditBtn(Button finishEditBtn) {
		this.finishEditBtn = finishEditBtn;
	}


	public void setTableNum(String table) {
		
		tableNumberLabel.setText("Table Number: "+table);
		
	}
	
	public TextField getOrderIdTextField() {
		
		return orderIdTextField;
		
	}
	
	public Button getCloseOrderBtn() {
		return closeOrderBtn;
	}


	public void setCloseOrderBtn(Button closeOrderBtn) {
		this.closeOrderBtn = closeOrderBtn;
	}


	public void setNumberOfPeople(int numberOfPeople ){

		numberOfPeopleLabel.setText("Number of People: "+ numberOfPeople);
	}
	
	public void setOrderId(int orderId){
		
		String orderIdStr = Integer.toString(orderId);
		
		orderIdTextField.setText(orderIdStr);
		
	}
	
	public TableView<OrderItems> getOrderTable(){
		
		return orderTable;
	}
	
	public void setOrderTable(ObservableList<OrderItems> orderItemsData){
		
		orderTable.setItems(orderItemsData);
	}
	
	public ChoiceBox<String> getRemItemDescription(){
		
		return remItemDescription;
	}
	
	public void setRemItemDescription(ObservableList<String> remItemChoice){
		
		remItemDescription.setItems(remItemChoice);
	}
	
	public ObservableList<OrderItems> getOrderItemsData(){
		
		return orderItemsData;
	}
	
	public void setOrderItemsData(ObservableList<OrderItems> orderItemsData){
		this.orderItemsData = orderItemsData;
		
	}

	public ObservableList<String> getRemItemChoice() {
		return remItemChoice;
	}

	public void setRemItemChoice(ObservableList<String> remItemChoice) {
		this.remItemChoice = remItemChoice;
	}



	public ChoiceBox<String> getAddItemDescription() {
		return addItemDescription;
	}



	public void setAddItemDescription(ChoiceBox<String> addItemDescription) {
		this.addItemDescription = addItemDescription;
	}



	public ObservableList<String> getAddItemChoice() {
		return addItemChoice;
	}



	public void setAddItemChoice(ObservableList<String> addItemChoice) {
		this.addItemChoice = addItemChoice;
	}



	public void setRemItemDescription(ChoiceBox<String> remItemDescription) {
		this.remItemDescription = remItemDescription;
	}



	
}//end of class
