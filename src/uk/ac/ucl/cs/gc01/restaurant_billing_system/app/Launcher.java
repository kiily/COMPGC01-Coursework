package uk.ac.ucl.cs.gc01.restaurant_billing_system.app;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 
 * 
 * <p>The Laucher class launches the application showing and displays the log in screen.<p>
 * 
 * <b>References:</b>
 * 
 * <ul>
 * <li>https://www.youtube.com/watch?v=cgv63JD7pfc</li>
 * <li>http://www.java2s.com/Code/Java/JavaFX/GetScreensize.htm</li>
 * <li>http://docs.oracle.com/javafx/scenebuilder/1/user_guide/jsbpub-user_guide.htm</li>
 * <li>http://docs.oracle.com/javafx/2/fxml_get_started/jfxpub-fxml_get_started.htm</li>
 *  <li>http://docs.oracle.com/javafx/2/get_started/hello_world.htm</li>
 * <li>http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm</li>
 * <li>http://docs.oracle.com/javafx/scenebuilder/1/user_guide/jsbpub-user_guide.htm</li>
 * <li>http://docs.oracle.com/javafx/scenebuilder/1/use_java_ides/sb-with-eclipse.htm#BABBFEBD</li>
 * <li>http://docs.oracle.com/javafx/scenebuilder/1/user_guide/menu-bar.htm</li>
 * <li>http://stackoverflow.com/questions/22207485/how-do-i-add-an-icon-to-the-alert-box</li>
 * </ul>
 * 
 * <p>General References:</p>
 * <ul>
 * <li> http://docs.oracle.com/javase/8/javase-clienttechnologies.htm</li>
 * </ul>
 * 
  * 
 * <b>CSS references:</b>
 * 
 * <ul>
 * <li>http://www.w3schools.com/cssref/</li>
 * <li>https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html</li>
 * <li>http://code.makery.ch/library/javafx-8-tutorial/part4/</li>
 * <li>http://www.quackit.com/css/css_color_codes.cfm</li>
 * <li>http://stackoverflow.com/questions/19788661/change-javafx-tab-default-look</li>
 * <li>https://shiny.rstudio.com/articles/css.html</li>
 * <li>http://stackoverflow.com/questions/10275841/how-to-change-the-icon-on-the-title-bar-of-a-stage-in-java-fx-2-0-of-my-applicat</li>
 * </ul>
 * 
 * <b>Javadoc References:</b>
 * <ul>
 * <li>http://www.w3schools.com/html/html_entities.asp</li>
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 * @version 2.0 
 *
 */
public class Launcher extends Application {
	
	
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Starts the application.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		try{

			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			
			
			primaryStage.getIcons().add(new Image((getClass().getClassLoader().getResource("restaurant-icon.png").toExternalForm())));

			primaryStage.setTitle("Restaurant Billing System");
			primaryStage.setScene(scene);
			primaryStage.show();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}