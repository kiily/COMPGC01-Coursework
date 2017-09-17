package uk.ac.ucl.cs.gc01.restaurant_billing_system.objects;

/**
 * 
 * 
 * <p> The Employee class used to generate the employees for our restaurant
 * It provides a template for the Manager class. Note that Employees have a unique empId and
 * username.</p>
 * 
 * <b>Fields:</b>
 * <ul>
 * 		<li>String firstname</li>
 * 		<li>String surname</li>
 * 		<li>String username</li>
 * 		<li>String password</li>
 * 		<li>String type</li>
 * 		<li>String lastLogin</li>
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class Employee {

		
	protected int empNumber = 0;
	
	
	protected String firstname = "";
	protected String surname ="";
	protected String username ="";
	protected String password = "";
	protected String type = "";
	protected String lastLogin="";
	
	
	/**
	 * 
	 * <b>Constructor:</b>
	 * 
	 * <p>Generates an Employee object with a 
	 * specified firstname, surname, username and password.</p>
	 * 
	 *
	 * @param firstname - employee's firstname.
	 * @param surname - employee's surname.
	 * @param username - employee's username.
	 * @param password - employee's password.
	 */
	public Employee(String firstname, String surname, String username,String password){
		
		this.firstname = firstname;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.type = this.getClass().getSimpleName();
		
		

	}

	
	//getters and setters
	
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public int getEmpNumber() {
		return empNumber;
	}

	public void setEmpNumber(int empNumber) {
		this.empNumber = empNumber;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getLastLogin() {
		return lastLogin;
	}


	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
}
