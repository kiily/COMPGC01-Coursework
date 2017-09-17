package uk.ac.ucl.cs.gc01.restaurant_billing_system.gui_testers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @deprecated
 * @author migue
 *
 */
public class AdminHomeGUIGenerator extends Application {

	
	 public static void main(String[] args) {
	        launch(args);
	    }
	    
	  @Override
	  public void start(Stage primaryStage) throws Exception {
	       
	    	try{
	        Parent root =  FXMLLoader.load(getClass().getResource("AdminHome.fxml"));
	     
	        Scene scene = new Scene(root);
	       // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        primaryStage.setTitle("Admin");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
}
