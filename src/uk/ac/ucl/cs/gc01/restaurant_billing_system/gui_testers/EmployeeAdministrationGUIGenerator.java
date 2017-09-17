package uk.ac.ucl.cs.gc01.restaurant_billing_system.gui_testers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uk.ac.ucl.cs.gc01.restaurant_billing_system.objects.Employee;


/**
 * @deprecated
 * @author migue
 *
 */
public class EmployeeAdministrationGUIGenerator extends Application  {

	
	//main window
	@FXML
	private AnchorPane empDataPane;
	@FXML
	private Button addEmpBtn;
	@FXML
	private Button remEmpBtn;
		
	@FXML
	private TableView<Employee> empDataTable;
	
	
	@FXML
	private TableColumn<Employee, Integer> column1;
	@FXML
	private TableColumn<Employee, String> column2;
	@FXML
	private TableColumn<Employee, String> column3;
	@FXML
	private TableColumn<Employee, String> column4;
	
	
	 public static void main(String[] args) {
	        launch(args);
	    }
	    
	  @Override
	  public void start(Stage primaryStage) throws Exception {
	       
	    	try{
	        Parent root =  FXMLLoader.load(getClass().getResource("EmpData.fxml"));
	     
	        Scene scene = new Scene(root);
	       // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        primaryStage.setTitle("Staff Manager");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	  
	  
	  
}
