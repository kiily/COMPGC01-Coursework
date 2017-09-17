package uk.ac.ucl.cs.gc01.restaurant_billing_system.objects;

/**
 * 
 * 
 * <p>The FoodItem class represents the different FoodItems on the menu.
 * Each instance variable corresponds to a column of the TableView in the Menu Manager 
 * screen.</p>
 * 
 * <b>Fields:</b>
 * <ul>
 * 	<li>int itemId</li>
 *  <li>String foodItemDescription</li>
 *  <li>String category</li>
 *  <li>double rate</li>
 *  </ul>
 *  
 *  
 * @author Miguel Marin Vermelho
 *
 */
public class FoodItem {
	
		
	private int itemId;
	private String foodItemDescription="";
	private String category ="";
	private double rate = 0.0;
	
	
	/**
	 *  <b>Constructor:</b>
	 *
	 * 
	 * <p> Generates an FoodItem object with a 
	 * specified foodItemDescription, sucategoryrname and rate.</p>
	 * 
	 * @param foodItemDescription - the food item's description.
	 * @param category - the food item's category (Main, Side, Drinks, Dessert).
	 * @param rate - the price of a single food Item.
	 */
	public FoodItem(String foodItemDescription, String category, double rate ){
		
		this.foodItemDescription = foodItemDescription;
		this.category =category;
		this.rate =rate;
	}

	
	//getters and setters

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getFoodItemDescription() {
		return foodItemDescription;
	}

	public void setFoodItemDescription(String foodItemDescription) {
		this.foodItemDescription = foodItemDescription;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}
