package uk.ac.ucl.cs.gc01.restaurant_billing_system.objects;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * 
 * 
 * <p> The DigitalClock class generates a Label which displays a live clock.</p>
 * 
 * References:
 * <ul>
 * <li>http://stackoverflow.com/questions/13227809/displaying-changing-values-in-javafx-label</li> 
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class DigitalClock extends Label {

	/**
	 * <b>Description:</b>
	 * 
	 * <p>This is the DgitalClock constructor, it creates a
	 * Digital clock object bound to the current time.</p>
	 */
	public DigitalClock() {
	    bindToTime();
	  }

	
	// creates the live clock
	  private void bindToTime() {
	    Timeline timeline = new Timeline(
	      new KeyFrame(Duration.seconds(0),
	        new EventHandler<ActionEvent>() {
	          @Override public void handle(ActionEvent actionEvent) {
	            Calendar time = Calendar.getInstance();
	            String timeStamp = new SimpleDateFormat("HH:mm:ss").format(time.getTime());
	    		String dateStamp = new SimpleDateFormat("dd/MM/yyyy").format(time.getTime());
	    		
	            String display = dateStamp+"  "+timeStamp;
	            setText(display);
	          }
	        }
	      ),
	      new KeyFrame(Duration.seconds(1))
	    );
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	  }
	  
	
	
}
