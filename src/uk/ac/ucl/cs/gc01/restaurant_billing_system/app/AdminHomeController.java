package uk.ac.ucl.cs.gc01.restaurant_billing_system.app;

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
import javafx.stage.Stage;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management.StaffManager;


/**
 * 
 * 
 * <p>This class provides the methods and elements of the GUI so 
 * that the user can act upon the elements defined in the FXML file for the Admin Home screen 
 * (AdminHome.fxml).
 * Accessibility to the FXML file is achieved with the following tag: @FXML. </p>
 * 
 * 
 * <p> Note that this class should be added as a controller to SceneBuilder and all the
 * elements should have their fx:id specified in the code section. This applies to all other controllers.</p> 
 * 
 * <p>It provides the following methods:</p>
 * <ul>
 * 		<li> <b>logOut(ActionEvent)</b> - triggered by pressing the log out button. Logs employee out and goes back to login screen.</li>
 * 		<li> <b>goToStaff(MouseEvent)</b> - triggered by pressing the staff button. Sends employee to Staff Manager screen.</li>
 * 		<li> <b>goToAddOrder(MouseEvent)</b> - triggered by pressing the add order region. Switches the screen to Tables screen.</li>
 * 		<li> <b>goToOrderHistory(MouseEvent)</b> - triggered by pressing the orders region. Switches the screen to Orders History screen.</li>
 * 		<li> <b>editMenu(MouseEvent)</b> - triggered by pressing the menu manager button. Sends employee to Menu Manager screen.</li>
 * </ul>		
 * 
 * <b>References:</b>
 * 
 * <ul>
 * <li>http://code.makery.ch/blog/javafx-dialogs-official/</li>
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class AdminHomeController {

	@FXML
	private Region ordersRegion;
	@FXML
	private Region staffRegion;
	@FXML
	private Region menuManagerRegion;
	@FXML
	private Region addOrderRegion;
	@FXML
	private Button logOutBtn;
	@FXML
	private Label adminHomeTitleLabel;
	
	private String currentUsername = LoginController.username;
	private String currentPassword = LoginController.password;
	
	//database manipulation
	private StaffManager staffManager = new StaffManager();
	
	/**
	 *  
	 * <p>Takes the user from the current screen back to the login screen, opening the
	 * Login.fxml file.</p>
	 * 
	 *  
	 * @param event - pressing the log out button.
	 */
	@FXML
	private void logOut(ActionEvent event){
		
		  
    	try{
        Parent root =  FXMLLoader.load(getClass().getResource("Login.fxml"));
     
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
     
		
		stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
        stage.setTitle("Restaurant Management System");
        stage.setScene(scene);
        stage.show();
        
        Stage stageToClose = (Stage) logOutBtn.getScene().getWindow();
        stageToClose.close();
        
        
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	/**
	 *  
	 * <p>Takes the user from the current screen to the Staff Manager screen, opening the
	 * EmpData.fxml file.</p>
	 * 
	 * @param click - pressing the staff region.
	 */
	@FXML
	private void goToStaff(MouseEvent click){
		
		  
    	try{
        Parent root =  FXMLLoader.load(getClass().getResource("Staff.fxml"));
     
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
		
		stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
        stage.setTitle("Staff Manager");
        stage.setScene(scene);
        stage.show();
        
        Stage stageToClose = (Stage) staffRegion.getScene().getWindow();
        stageToClose.close();
        
        
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	
	
	
	/**
	 * 	 * 
	 * <p>Takes the user from the current screen to the tables screen, opening the
	 * Tables.fxml file.</p>
	 * 
	 * @param click - pressing the add order region.
	 * @throws SQLException - SQL handling exception.
	 */
	@FXML
	private void goToAddOrder(MouseEvent click) throws SQLException{
		
		int currentEmpId = staffManager.retrieveConnectedEmpId(currentUsername, currentPassword);

		try{

			FXMLLoader loader2 = new FXMLLoader();
			Parent root =  loader2.load(getClass().getResource("Tables.fxml").openStream());
			TablesController tablesController = (TablesController) loader2.getController();

			tablesController.disableBtn(TablesController.getStaffType());
			tablesController.setEmpId(currentEmpId);





			Stage stage = new Stage();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			stage.setTitle("Orders");
			stage.setScene(scene);
			stage.show();

			Stage stageToClose = (Stage) addOrderRegion.getScene().getWindow();
			stageToClose.close();


		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	 * 
	 * <p>Takes the user from the current screen to the Order History screen, opening the
	 * OrderHistory.fxml file.</p>
	 * 
	 * @param click - pressing the orders region.
	 * @throws SQLException - SQL handling exception.
	 *
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

	
	/**
	 *  
	 * <p>Takes the user from the current screen to the Menu Manager screen, opening the
	 * Menu.fxml file.</p>
	 * 
	 * 
	 * @param click - pressing the Menu Manager region.
	 */ 
	@FXML
	private void editMenu(MouseEvent click){
		
		  
		try{
			Parent root =  FXMLLoader.load(getClass().getResource("Menu.fxml"));

			Stage stage = new Stage();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
				
			stage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));
			stage.setTitle("Menu Editor");
			stage.setScene(scene);
			stage.show();

			Stage stageToClose = (Stage) menuManagerRegion.getScene().getWindow();
			stageToClose.close();


		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
}
