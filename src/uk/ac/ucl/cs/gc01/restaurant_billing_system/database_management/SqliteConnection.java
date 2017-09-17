package uk.ac.ucl.cs.gc01.restaurant_billing_system.database_management;

import java.sql.*;


/**
 * 
 * 
 * <p>This class sets up a connection to the database attached in the source folder: 
 * (RestaurantBillingSystem.db). It allows the JavaFX application to access the SQLite database</p>
 * 
 * <b>References:</b>
 * 
 * <ul>
 * <li>https://www.youtube.com/watch?v=nTm1rzhHOc8&#38;index=32&#38;list=PLS1QulWo1RIaUGP446_pWLgTZPiFizEMq;</li>
 * <li>http://docs.gluonhq.com/samples/gluonsqlite/</li>
 * <li>https://github.com/gluon-samples/gluon-SQLite</li>
 * <li>http://www.sqlitetutorial.net/sqlite-java/</li>
 * <li>http://www.java2s.com/Code/Java/Database-SQL-JDBC/Outputdatafromtable.htm</li>
 * </ul>
 * 
 * @author Miguel Marin Vermelho
 *
 */
public class SqliteConnection {

	/**
	 * <b>Description</b>
	 * 
	 * <p>This method connects the app to the SQLite database</p>
	 * @return Connection - established connection to database
	 */
	public static Connection Connector( ){ 
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:RestaurantBillingSystemNew.sqlite");
			return conn;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


	
}


