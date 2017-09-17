package uk.ac.ucl.cs.gc01.restaurant_billing_system.app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.StaffManager;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.DigitalClock;



/** 
 *
 * 
 * <p>The LoginController class provides the methods and elements of the GUI so 
 * that the user can act upon the elements defined in the FXML file for the login screen 
 * (Login.fxml).
 * Accessibility to the FXML file is achieved with the following tag: @FXML. </p>
*	
 * <p> Note that this class should be added as a controller to SceneBuilder and all the
 * elements should have their fx:id specified in the code section. This applies to all other controllers.</p> 
 * 
 * <p>It provides the following methods:</p>
 * <ul>
 * 		<li> <b>exitApp(ActionEvent)</b> - triggered by pressing the exit button. Exits the application.</li>
 * 		<li> <b>login(ActionEvent)</b> - triggered by pressing the login button. Sends employee to Employee or Admin home screen depending on Employee type.</li>
 * </ul>		
 * 
 * <b>References:</b>
 * 
 * <ul>
 * <li>https://www.youtube.com/watch?v=mokD1I7hl-o&#38;list=PLS1QulWo1RIbfTjQvTdj8Y6yyq4R7g-Al&#38;index=55;</li>
 * <li>http://code.makery.ch/blog/javafx-dialogs-official/</li>
 * <li>http://stackoverflow.com/questions/23878623/how-do-you-add-to-an-existing-scene-in-javafx</li>
 * <li>https://www.youtube.com/watch?v=C5H5GsgVE8U</li>
 * <li>http://stackoverflow.com/questions/10275841/how-to-change-the-icon-on-the-title-bar-of-a-stage-in-java-fx-2-0-of-my-applicat</li>
 * <li>https://www.youtube.com/watch?v=9YrmON6nlEw</li>
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class LoginController  {

	/*@FXML tags class or members as accessible to markup 
	 * i.e. connects FXML file to login controller. We define the different
	 * GUI elements of the login screen.
	 */
	
	@FXML
	private AnchorPane logInPane;
	@FXML
	private Button logInButton;
	@FXML
	private Button exitButton;
	@FXML
	private Button adminLoginBtn;
	@FXML
	private Label lblStatus;
	@FXML
	private Label isConnected;
	@FXML
	private Label timeLabel;
	@FXML
	private Label title;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private DigitalClock digitalClock;
	
	//the controller class registers employee information
	public static String username;
	public static String password;
	public static int empId;
	public static String loginTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	
	//variables for DATABASE connection
	private StaffManager staffManager = new StaffManager();



	/**
	 * 
	 * 
	 * <p>Exits the application when 
	 * pressing the exit button.</p>
	 * 
	 * @param exit - pressing the exit button.
	 */
	@FXML
	private void exitApp(ActionEvent exit){
		System.exit(0);
	}


	/**
	 * 
	 * <p> The login methods extracts the text on the username and password fields
	 * and passes it to the retrievedConnectedEmpId method which retrieves the ID
	 * of the employee that last connected to the application.
	 * 
	 * It updates the static fields: username, password and empId of the LoginController class.
	 * The username and password are then checked against the database (attached). 
	 * If the login credentials correspond to an Admin, the AdminHome.fxml file is opened; if they
	 * correspond to an employee, the EmployeeHome.fxml file is opened.
	 * 
	 * Lastly, if an Employee type logs in, this method sets the employee name and number 
	 * next screen.</p>
	 * 
	 * <p>Also displays an Alert if invalid credentials are entered.</p>
	 * 
	 *  
	 * @param event -- pressing the log in button
	 */
	@FXML
	private void login(ActionEvent event){

		try {
			String username = usernameField.getText();
			String password = passwordField.getText();

			int empID = staffManager.retrieveConnectedEmpId(username, password);

			//updating static variables
			LoginController.username = username;
			LoginController.password = password;
			LoginController.empId= empID;

			staffManager.updateLastLogin();


			//check if login is admin
			if(staffManager.isAdminLogin(username, password)){


				

				try{


					//moves to admin home screen
					AnchorPane root = FXMLLoader.load(getClass().getResource("AdminHome.fxml"));
					
					Stage tablesStage = new Stage();
					Scene scene = new Scene(root);
					
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					
					
					tablesStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
					tablesStage.setTitle("Restaurant Billing System");
					tablesStage.setScene(scene);
					tablesStage.show();

					
					
					//close current screen
					Stage stageToClose = (Stage) logInButton.getScene().getWindow();
					stageToClose.close();


				}catch(Exception e){
					e.printStackTrace();
				}

				//check if login is employee
			}else if(staffManager.isLogin(username, password)){



			

				try{

					FXMLLoader loader2 = new FXMLLoader();	

					Parent root = loader2.load(getClass().getResource("EmployeeHome.fxml").openStream());
					EmployeeHomeController employeeHomeController = (EmployeeHomeController) loader2.getController();

					employeeHomeController.setEmpID(empID);
					employeeHomeController.setEmployeeName(empID);

					Stage Stage = new Stage();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					
									
					Stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
					Stage.setTitle("Restaurant Billing System - Employee Home Menu");
					Stage.setScene(scene);
					Stage.show();

					Stage stageToClose = (Stage) logInButton.getScene().getWindow();
					stageToClose.close();

				}catch(IOException e){
					e.printStackTrace();
				}

			//if login not in the database
			} else{

				
				Alert alert = new Alert(AlertType.WARNING);

								
				Stage tempStage = (Stage) alert.getDialogPane().getScene().getWindow();
				tempStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
				
				alert.setTitle("Restaurant Billing System - WARNING");
				alert.setHeaderText("Invalid Login Credentials - Login Failed");
				alert.setContentText("Please enter valid credentials or contact administrator for support!");
				alert.showAndWait();
				
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	
}
