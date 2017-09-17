package uk.ac.ucl.cs.gc01.restaurant_billing_system.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

import uk.ac.ucl.cs.gc01.restaurant_billing_system.app.LoginController;

/**
 * 
 * 
 * <p>The OrderDetails class represents the different details contained in an order.
 * Each instance variable corresponds to a column of the order details table in the database.</p> 
 *
 * <b>Fields:</b>
 * <ul>
 * 	<li>int tableNumber</li>
 *  <li>int orderId</li>
 *  <li>String orderTime</li>
 *  <li>String orderDate</li>
 *  <li>String specialRequests</li>
 *  <li>String comments</li>
 *  </ul>
 *  
 *  <b>References:</b>
 *  http://stackoverflow.com/questions/23068676/how-to-get-current-timestamp-in-string-format-in-java-yyyy-mm-dd-hh-mm-ss
 *  
 * @author Miguel Marin Vermelho
 *
 */
public class OrderDetails {

	
		
	//help track employees who make orders
	private int empId;
	
	private int tableNumber;
	private int orderId = 0;
	private String orderTime="";
	private String orderDate="";
	private String specialRequests ="";
	private String comments = "";
	
	
	/**
	 * <b>Constructor:</b>
	 *
	 * 
	 * <p> Generates an OrderDetails object with a 
	 * specified empId and a timestamp of date and time at which the object was 
	 * created.</p>
	 * 
	 */
	public OrderDetails(){
		
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
		String dateStamp = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		
		this.orderTime = timeStamp;
		this.orderDate = dateStamp;
		this.empId= LoginController.empId;
				
		
	//getters and setters
		
	}
	public int getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getSpecialRequests() {
		return specialRequests;
	}
	public void setSpecialRequests(String specialRequests) {
		this.specialRequests = specialRequests;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	

}
