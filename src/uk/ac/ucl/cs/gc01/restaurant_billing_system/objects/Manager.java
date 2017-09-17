package uk.ac.ucl.cs.gc01.restaurant_billing_system.objects;

/**
 *
 * 
 * <p>The Manager class allows to distinguish a manager from an employee. It inherits all varibles from
 * the Employee class.</p>
 * 
 * 
 *  <b>Fields:</b>
 * <ul>
 * 		<li>String firstname</li>
 * 		<li>String surname</li>
 * 		<li>String username</li>
 * 		<li>String password</li>
 * 		<li>String type</li>
 * </ul>
 *
 * @author Miguel Marin Vermelho
 *
 */
public class Manager extends Employee{
	


	/**
	 * <b>Constructor:</b>
	 *
	 * 
	 * <p> Generates an Manager object with a 
	 * specified firstname, surname, userId and password.</p>
	 * 
	 * @param firstname - Manager's firstname.
	 * @param surname - Manager's surname.
	 * @param userid - Manager's userid.
	 * @param password - Manager's password.
	 */
	public Manager(String firstname, String surname, String userid, String password) {
		super(firstname, surname, userid, password);
		
	}
	
}	
	