package uk.ac.ucl.cs.gc01.restaurant_billing_system.objects;

/**
 * 
 * <p>The OrderItems class represents the different details contained in an order as well as the details about
 * the items in those orders. 
 * Each instance variable corresponds to a column of the order details table joined with the fooditems tables
 * in the database.</p> 
 *
 * <b>Fields:</b>
 * <ul>
 * 	<li>String foodItem</li>
 *  <li>int quantity</li>
 *  <li>double rate</li>
 *  <li>String specialRequests</li>
 *  <li>String comments</li>
 * </ul>
 *  
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class OrderItems {
	
	
	private String foodItem;
	private int quantity;
	private double rate;
	private String specialRequests;
	private String comments;
	
	/**
	 * <b>Constructor:</b>
	 *
	 * 
	 * <p>Generates an OrderItems object with a 
	 * specified foodItem description, foodItem quantity, rate, special requests and
	 * comments.</p>
	 * 
	 * @param foodItem - the food item description.
	 * @param quantity - the quantity of the specified food item to add.
	 * @param rate - the price of a single food item.
	 * @param specialRequests - special requests attached to order.
	 * @param comments - comments attached to order.
	 */
	public OrderItems(String foodItem, int quantity, double rate, String specialRequests, String comments){
		
		this.foodItem = foodItem;
		this.quantity = quantity;
		this.rate = rate;
		this.specialRequests = specialRequests;
		this.comments = comments;
	}

	
	//getters and setters
	
	public String getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(String foodItem) {
		this.foodItem = foodItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
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
	
}
