package uk.ac.ucl.cs.gc01.restaurant_billing_system.app;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.StaffManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.TableManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.Table;

/**
 *
 * 
 * <p>The EmployeeHomeController class provides the methods and elements of the GUI so 
 * that the user can act upon the elements defined in the FXML file for the Employee Home screen
 * (EmployeeHome.fxml).
 * Accessibility to the FXML file is achieved with the following tag: @FXML. </p>
 * <p>It also provides getters and setters for the GUI elements.</p>
 * 
 * <p> Note that this class should be added as a controller to SceneBuilder and all the
 * elements should have their fx:id specified in the code section. This applies to all other controllers.</p> 
 * 
 * <p>It provides the following methods:</p>
 * <ul>
 * 		<li> <b>setEmpID(int)</b> - sets empId text to empId Label.</li>
 * 		<li> <b>setEmployeeName(int)</b> - sets the empoyees firstname on the welcome label.</li>
 * 		<li> <b>logOut(ActionEvent)</b> - triggered by pressing the log out button. Logs employee out and goes back to login screen. </li>
 * 		<li> <b>goToAddOrder(MouseEvent)</b> - triggered by pressing the add order region. Switches the screen to Tables screen.</li>
 * 		<li> <b>goToOrderHistory(MouseEvent)</b> - triggered by pressing the orders region. Switches the screen to Orders History screen. </li>
 * </ul>		
 * 
 * 
 * <b>References:</b>
 * 
 * <ul>
 * <li>http://code.makery.ch/blog/javafx-dialogs-official/</li>
 * <li>https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/Region.html</li>
 * </ul>
 * 
 *  
 * @author Miguel Marin Vermelho
 *
 *
 */
public class EmployeeHomeController {

	
	@FXML
	private Label welcomeLabel;
	@FXML
	private Label empIdLabel;
	@FXML
	private Label ordersLabel;
	@FXML
	private Label addOrderLabel;
	@FXML
	private Button logOutBtn;
	@FXML
	private Region addOrderRegion;
	@FXML
	private Region ordersRegion;
	
	private String currentUsername = LoginController.username;
	private String currentPassword = LoginController.password;
	
	//database manipulators
	private StaffManager staffManager = new StaffManager();
	private TableManager tableManager = new TableManager();
	
	
	/**
	 *  
	 * <p>Takes the current empId as an argument and changes the text on the 
	 * empIdLabel to display this empId.</p>
	 * 
	 * @param empId - the current employee ID.
	 */
	public void setEmpID(int empId){
		
		empIdLabel.setText("EmpID: "+Integer.toString(empId));
		empIdLabel.setFont(new Font("System", 24));
				
	}
	
	/**
	 *  
	 * <p>Takes the current empId as an argument and uses this to retrieve the
	 * employee's firstname and surname and displays them in the welcome label.</p>
	 * 
	 * @param empID - the current employee ID.
	 * @throws SQLException - SQL handling exception.
	 */
	public void setEmployeeName(int empID) throws SQLException{
		
		String[] fullName = staffManager.retrieveEmpName(empID);
		
		String firstname = fullName[0];
		String surname = fullName[1];
		
		
		welcomeLabel.setText("Welcome, "+firstname+" "+surname);
		welcomeLabel.setFont(new Font("System", 22));
		
		
	}
	
	
	/**
	 * 
	 * 
	 * <p>This method takes the user from the current screen back to the login screen, opening the
	 * Login.fxml file.</p>
	 * 
	 * @param click - pressing the log out button.
	 */
	@FXML
	private void logOut(ActionEvent click){
		
		
    	try{
			
			Parent root1 = FXMLLoader.load(getClass().getResource("Login.fxml"));
	     
	        Stage stage = new Stage();
	        Scene scene = new Scene(root1);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
	    			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
	        stage.setTitle("Restaurant Billing System");
	        stage.setScene(scene);
	        stage.show();
	        
	        Stage stageToClose = (Stage) logOutBtn.getScene().getWindow();
	        stageToClose.close();
	        
	        
	        
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	}
	
	/**
	 *
	 * <p>Takes the user from the current screen to the tables screen, opening the
	 * Tables.fxml file.</p>
	 * 
	 * @param click - pressing the add order region.
	 * @throws SQLException - SQL handling exception.
	 */
	@FXML
	private void goToAddOrder(MouseEvent click) throws SQLException{
		
		 Stage stageToClose = (Stage) addOrderRegion.getScene().getWindow();
	     stageToClose.close();
	     
	     int currentEmpId = staffManager.retrieveConnectedEmpId(currentUsername, currentPassword);
	     
		try{
			
			FXMLLoader loader2 = new FXMLLoader();
			
	        Parent root =  loader2.load(getClass().getResource("Tables.fxml").openStream());
	        TablesController tablesController = (TablesController) loader2.getController();
	        
	        tablesController.disableBtn(TablesController.getStaffType());
	        tablesController.setEmpId(currentEmpId);
	        
	        
	        for(int tableNum =1; tableNum<=7; tableNum++ ){
	    		
				try {

					int numberOfPeople = tableManager.getNumberOfPeople(tableNum);

					Table table = new Table();

					table.setNumberOfPeople(numberOfPeople);
					table.setTableNumber(tableNum);

					
					tablesController.updateNumberOfPeople(table, numberOfPeople);
					


				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	        
	     
	        Stage stage = new Stage();
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
	      
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
	        stage.setTitle("Orders");
	        stage.setScene(scene);
	        stage.show();
	        
	       
	        
	        
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	}
	
	/**
	 * 
	 *<p>Takes the user from the current screen to the Order History screen, opening the
	 * OrderHistory.fxml file.</p>
	 * 
	 * @param click - pressing the orders region.
	 * @throws SQLException - SQL handling exception.
	 */
	@FXML
	private void goToOrderHistory(MouseEvent click) throws SQLException{
		
		 Stage stageToClose = (Stage) addOrderRegion.getScene().getWindow();
	     stageToClose.close();
	     

	     
		try{
		
			
	        Parent root =  FXMLLoader.load(getClass().getResource("OrderHistory.fxml"));
	       
	     
	        Stage stage = new Stage();
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
	       
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			stage.setTitle("Order History");
	        stage.setScene(scene);
	        stage.show();
	        
	       
	        
	        
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	}
	
}
