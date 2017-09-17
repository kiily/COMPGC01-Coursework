package uk.ac.ucl.cs.gc01.restaurant_billing_system.app;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.OrderManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.StaffManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.TableManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.OrderDetails;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.OrderItems;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.Table;

/**
 * 
 * 
 * 
 * <p>The TablesController class provides the methods and elements of the GUI so 
 * that the user can act upon the elements defined in the FXML file for the Tables screen
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
 * 		<li><b>orderItem(MouseEvent)</b> - triggered by pressing a table label. Sends user back to Orders screen.</li>
 *		<li><b>goToAdminHome(ActionEvent)</b> - triggered by pressing the Admin Home button. Sends user back to AdminHome screen.</li>
 *		<li><b>goToEmployeeHome(ActionEvent)</b> - triggered by pressing the Employee Home button. Sends user back to EmployeeHome screen.</li>
 *		<li><b>disableBtn(String)</b> - disables the appropriate buttons dependending on the employee type</li>
 *		<li><b>changeToBusy(Table)</b> - updates the colors of the rectangles representing the tables depending on their status; red - busy;
 *green - free.</li>
 *		<li><b>updateNumberOfPeople(Table, int)</b> - updates the number of people in the table capacity labels.</li>
 * </ul>	
 * 
 * <b>References:</b>
 * <ul>
 * <li> http://code.makery.ch/blog/javafx-dialogs-official/</li>
 * </ul>
 *  
 * @author Miguel Marin Vermelho
 *
 */
public class TablesController implements Initializable  {
	
	

	/*@FXML tags class or members as accessible to markup 
	 * i.e. connects FXML file to tables controller. We define the different
	 * GUI elements of the tables screen.
	 */
	
	@FXML
	private Label nameLabel;
	@FXML
	private TextField empIdTextField;
	//table1
	@FXML
	private Rectangle tableRectangle1;
	@FXML
	private Label table1;
	@FXML
	private Label capacity1;
	//table2
	@FXML
	private Rectangle tableRectangle2;
	@FXML
	private Label table2;
	@FXML
	private Label capacity2;
	//table3
	@FXML
	private Rectangle tableRectangle3;
	@FXML
	private Label table3;
	@FXML
	private Label capacity3;
	//table4
	@FXML
	private Circle tableCircle4;
	@FXML
	private Label table4;
	@FXML
	private Label capacity4;
	//table5
	@FXML
	private Rectangle tableRectangle5;
	@FXML
	private Label table5;
	@FXML
	private Label capacity5;
	//table6
	@FXML
	private Rectangle tableRectangle6;
	@FXML
	private Label table6;
	@FXML
	private Label capacity6;
	//table7
	@FXML
	private Rectangle tableRectangle7;
	@FXML
	private Label table7;
	@FXML
	private Label capacity7;
	@FXML
	private Button adminHomeBtn;
	@FXML
	private Button employeeHomeBtn;
	
	//database manipulators
	private StaffManager staffManager = new StaffManager();
	private TableManager tableManager = new TableManager();
	private OrderManager ordersManager = new OrderManager();
	
	
	private String currentUsername = LoginController.username;
	private String currentPassword = LoginController.password;
	
	
	private OrderDetails order = null;
	
	
	//static variables
	public static String staffType ="";
	public static int tableNumber =0;
	public static int empId = 0;
	
	
	
	static Table tableObj1 = new Table(), tableObj2= new Table(), tableObj3= new Table(), tableObj4= new Table(), tableObj5= new Table(), tableObj6= new Table(), tableObj7 = new Table();
	

	/**
	 * <p>Initializes the GUI and updates the table status as well as the number of people based
	 * on data that the method retrieves from the database.</p>
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		for(int tableNum =1; tableNum<=7; tableNum++ ){
			boolean status = true;
			try {
				status = tableManager.getTableStatus(tableNum);
				
				Table tempTable = new Table();
				tempTable.setStatus(status);
				tempTable.setTableNumber(tableNum);
				
				
				
				if(status == false){
					
					switch(tableNum){

					case 1:
						tableRectangle1.setFill(Color.RED);
						break;
					case 2:
						tableRectangle2.setFill(Color.RED);
						break;
					case 3:
						tableRectangle3.setFill(Color.RED);
						break;
					case 4:
						tableCircle4.setFill(Color.RED);
						break;
					case 5:
						tableRectangle5.setFill(Color.RED);
						break;
					case 6:
						tableRectangle6.setFill(Color.RED);
						break;
					case 7:
						tableRectangle7.setFill(Color.RED);
						break;
					}
				}


			} catch (SQLException e) {
				e.printStackTrace();
				}
			}
		
		for(int tableNum =1; tableNum<=7; tableNum++ ){
		
			try {

				int numberOfPeople = tableManager.getNumberOfPeople(tableNum);

				Table table = new Table();

				table.setNumberOfPeople(numberOfPeople);
				table.setTableNumber(tableNum);
				
				int capacity = tableManager.getTableCapacity(table);
				
				switch(table.getTableNumber()){
				
				case 1:
					capacity1.setText(numberOfPeople+"/"+capacity);
					break;
				case 2:
					capacity2.setText(numberOfPeople+"/"+capacity);
					break;
				case 3:
					capacity3.setText(numberOfPeople+"/"+capacity);
					break;
				case 4:
					capacity4.setText(numberOfPeople+"/"+capacity);
					break;
				case 5:
					capacity5.setText(numberOfPeople+"/"+capacity);
					break;
				case 6:
					capacity6.setText(numberOfPeople+"/"+capacity);
					break;
				case 7:
					capacity7.setText(numberOfPeople+"/"+capacity);
					break;
				}


			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	/**
	 * <p>Registers the tableNumber from the selected table and
	 * prompts the user to specify the number of people. If this number is above the capacity,
	 * the operation is cancelled and an Alert is displayed. The method behaves differently depending
	 * on whether the table is free or busy. If free, the method creates a new order ready store be stored;
	 * if busy, pressing the table again goes back to the current order at that table.</p>
	 * 
	 * 
	 * @param event - pressing a table label.
	 * @return boolean methodCall - to check whether method was called successfully. 
	 * @throws Exception - error occuring in the method.
	 */
	@FXML
	private boolean orderItem(MouseEvent event) throws Exception{
		
		boolean methodCall = true;
		Table table = null;
		Label tableNumber = (Label) event.getSource();

		int tableNum = Integer.valueOf(tableNumber.getText());
		int currentEmpId = (LoginController.empId);

		//empId and tableNum correspond to the current employee and table clicked
		boolean status = tableManager.getTableStatus(tableNum);

		int numberOfPeople=0;

		if (status == true){

			//dialog pops and asks for number of people		
			TextInputDialog dialog = new TextInputDialog();



			Stage tempStage = (Stage) dialog.getDialogPane().getScene().getWindow();
			tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
		

			dialog.setTitle("Number of People");
			dialog.setHeaderText("Please enter the number of people");
			dialog.setContentText("Number of People:");

			
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				numberOfPeople = Integer.valueOf(result.get());

				table = new Table(tableNum, status, numberOfPeople);
				TablesController.setTableNumber(table.getTableNumber());


			}

			else{
				methodCall = false;
				dialog.close();
			}

			//if table is busy
		}else if (status == false){

			numberOfPeople = tableManager.getNumberOfPeople(tableNum);




			table = new Table(tableNum, status, numberOfPeople);
			tableManager.updateNumberOfPeople(table);
			TablesController.setTableNumber(table.getTableNumber());
		}



		try{

			int capacity = tableManager.getTableCapacity(table);


			//ensures that number of people does not exceed capacity
			if(table.getNumberOfPeople() <= capacity){

				FXMLLoader loader2 = new FXMLLoader();
				Parent root = loader2.load(getClass().getResource("Orders.fxml").openStream());
				OrdersController ordersNewController = (OrdersController) loader2.getController();


				//if table is free
				if(status == true ){


					//object with appropriate time created inside generateOrder
					order = ordersManager.generateOrderRecord(tableNum, currentEmpId);


					ordersNewController.setTableNum(Integer.toString(table.getTableNumber()));
					ordersNewController.setNumberOfPeople(table.getNumberOfPeople());

					order.setOrderId(ordersManager.getOrderId(order, table.getTableNumber()));


					ordersNewController.setOrderId(order.getOrderId());

					Button backBtn = ordersNewController.getBackBtn();
					backBtn.setVisible(false);
					ordersNewController.setBackBtn(backBtn);

					Button closeOrderBtn = ordersNewController.getCloseOrderBtn();
					closeOrderBtn.setDisable(true);
					ordersNewController.setCloseOrderBtn(closeOrderBtn);


					switch(tableNum){

					case 1:
						tableObj1.setCurrentOrderId(order.getOrderId());
						tableObj1.setTableNumber(tableNum);
						tableManager.updateCurrentOrderId(tableObj1);
						break;
					case 2:
						tableObj2.setCurrentOrderId(order.getOrderId());
						tableObj2.setTableNumber(tableNum);
						tableManager.updateCurrentOrderId(tableObj2);
						break;
					case 3:
						tableObj3.setCurrentOrderId(order.getOrderId());
						tableObj3.setTableNumber(tableNum);
						tableManager.updateCurrentOrderId(tableObj3);
						break;
					case 4:
						tableObj4.setCurrentOrderId(order.getOrderId());
						tableObj4.setTableNumber(tableNum);
						tableManager.updateCurrentOrderId(tableObj4);
						break;
					case 5:
						tableObj5.setCurrentOrderId(order.getOrderId());
						tableObj5.setTableNumber(tableNum);
						tableManager.updateCurrentOrderId(tableObj5);
						break;
					case 6:
						tableObj6.setCurrentOrderId(order.getOrderId());
						tableObj6.setTableNumber(tableNum);
						tableManager.updateCurrentOrderId(tableObj6);
						break;
					case 7:
						tableObj1.setCurrentOrderId(order.getOrderId());
						tableObj7.setTableNumber(tableNum);
						tableManager.updateCurrentOrderId(tableObj7);
						break;

					}

					//table is busy
				}else if (status == false){


					int orderId = tableManager.getCurrentOrderId(table);
					ordersNewController.setTableNum(Integer.toString(table.getTableNumber()));
					ordersNewController.setNumberOfPeople(table.getNumberOfPeople());
					ordersNewController.setOrderId(orderId);

					ObservableList<OrderItems> orderItemsData;
					orderItemsData = ordersManager.buildOrderItemsData(orderId);
					ordersNewController.setOrderTable(orderItemsData);


				}



				Button finishEditBtn = ordersNewController.getFinishEditBtn();
				finishEditBtn.setDisable(true);
				finishEditBtn.setVisible(false);

				Button cancelOrderBtn = ordersNewController.getCancelOrderBtn();

				ordersNewController.setFinishEditBtn(finishEditBtn);
				ordersNewController.setCancelOrderBtn(cancelOrderBtn);

				Stage ordersStage = new Stage();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


				ordersStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
				ordersStage.setTitle("Orders");
				ordersStage.setScene(scene);
				ordersStage.show();

				Stage stageToClose = (Stage) adminHomeBtn.getScene().getWindow();
				stageToClose.close();


			}else{
				Alert alert1 = new Alert(AlertType.WARNING);

				Stage tempStage = (Stage) alert1.getDialogPane().getScene().getWindow();
				tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
				alert1.setTitle("Restaurant Billing System - WARNING");
				alert1.setHeaderText("Table capacity exceeded");
				alert1.setContentText("The number of people entered exceeds the table's capacity\nPlease try again!");

				alert1.showAndWait();
			}




		}catch(Exception e){
			e.printStackTrace();
		}
		return methodCall;

	}


	/**
	 * <p>Takes the user (admin) from the current screen back to the Admin Home screen, opening the
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
	 * <p>Takes the user (employee) from the current screen back to the Employee Home screen, opening the
	 * EmployeeHome.fxml file.</p>
	 * 
	 * @param click - pressing the employee home button.
	 * @throws SQLException - SQL handling exception.
	 */
	@FXML
	private void goToEmployeeHome(ActionEvent click) throws SQLException{
		Stage stageToClose = (Stage) employeeHomeBtn.getScene().getWindow();
		stageToClose.close();

		int empID = staffManager.retrieveConnectedEmpId(currentUsername, currentPassword);
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




		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * <p>Takes a String representing the employee type and disables
	 * the appropriate buttons dependending on the type i.e. if Employee = Manager AdminHome - enabled
	 * EmployeeHome -- disabled and vice-versa.</p>
	 * 
	 * @param type - the type of the currently connected employee.
	 */
	public void disableBtn(String type){


		adminHomeBtn.setDisable(false);
		employeeHomeBtn.setDisable(false);

		if(type.equalsIgnoreCase("Employee")){
			adminHomeBtn.setDisable(true);
		}else if (type.equalsIgnoreCase("Manager")){
			employeeHomeBtn.setDisable(true);

		}
	}


	/**
	 * <p>Changes the color of the table shape (rectangle or circle)
	 * based on the status of the Table object passed to the method.</p>
	 * 
	 * @param table - Table object.
	 */
	public void changeToBusy(Table table){
		
		
		if(table.isStatus() == false ){
					
		switch(table.getTableNumber()){
		
		case 1:
			tableRectangle1.setFill(Color.RED);
			break;
		case 2:
			tableRectangle2.setFill(Color.RED);
			break;
		case 3:
			tableRectangle3.setFill(Color.RED);
			break;
		case 4:
			tableCircle4.setFill(Color.RED);
			break;
		case 5:
			tableRectangle5.setFill(Color.RED);
			break;
		case 6:
			tableRectangle6.setFill(Color.RED);
			break;
		case 7:
			tableRectangle7.setFill(Color.RED);
			break;
		}
		
	}
	}
	
	/**
	 * <p>Updates the number of people in the capacity labels
	 * based on the current status of the table as well as the number of people.</p>
	 * 
	 * @param table - Table Object.
	 * @param numberOfPeople - the number of people of the selected table.
	 * @throws SQLException - SQL handling exception.
	 */
	public void updateNumberOfPeople(Table table, int numberOfPeople) throws SQLException{
		
		
		int capacity = tableManager.getTableCapacity(table);
				
		
		 
		switch(table.getTableNumber()){
		
		case 1:
			capacity1.setText(numberOfPeople+"/"+capacity);
			break;
		case 2:
			capacity2.setText(numberOfPeople+"/"+capacity);
			break;
		case 3:
			capacity3.setText(numberOfPeople+"/"+capacity);
			break;
		case 4:
			capacity4.setText(numberOfPeople+"/"+capacity);
			break;
		case 5:
			capacity5.setText(numberOfPeople+"/"+capacity);
			break;
		case 6:
			capacity6.setText(numberOfPeople+"/"+capacity);
			break;
		case 7:
			capacity7.setText(numberOfPeople+"/"+capacity);
			break;
		}
		
	}



	//getters and setters for GUI elements
	
	public void setEmpId(int empId){
		
		
		TablesController.empId=empId;
		
		String text = Integer.toString(empId);
		
		empIdTextField.setText(text);
		empIdTextField.setDisable(true);
		
		
	}
	

	public Label getCapacity1() {
		return capacity1;
	}



	public void setCapacity1(Label capacity1) {
		this.capacity1 = capacity1;
	}



	public Label getCapacity2() {
		return capacity2;
	}



	public void setCapacity2(Label capacity2) {
		this.capacity2 = capacity2;
	}



	public Label getCapacity3() {
		return capacity3;
	}



	public void setCapacity3(Label capacity3) {
		this.capacity3 = capacity3;
	}



	public Label getCapacity4() {
		return capacity4;
	}



	public void setCapacity4(Label capacity4) {
		this.capacity4 = capacity4;
	}



	public Label getCapacity5() {
		return capacity5;
	}



	public void setCapacity5(Label capacity5) {
		this.capacity5 = capacity5;
	}



	public Label getCapacity6() {
		return capacity6;
	}



	public void setCapacity6(Label capacity6) {
		this.capacity6 = capacity6;
	}



	public Label getCapacity7() {
		return capacity7;
	}



	public void setCapacity7(Label capacity7) {
		this.capacity7 = capacity7;
	}



	public static String getStaffType() {
		return staffType;
	}



	public static void setStaffType(String staffType) {
		TablesController.staffType = staffType;
	}



	public static int getTableNumber() {
		return tableNumber;
	}



	public static void setTableNumber(int tableNumber) {
		TablesController.tableNumber = tableNumber;
	}
	


}
