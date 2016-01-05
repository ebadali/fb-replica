package dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

	static Connection con;
	static String url;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:8080/dataJan-1-2016";
	// Database credentials
	static final String USER = "username";
	static final String PASS = "password";

	public static Connection getConnection(String path) {

		try {            
			
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:"+path+"db/socialmedia";
			try {

				con = DriverManager.getConnection(dbURL);
					
			}

			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		catch (ClassNotFoundException e) {
			System.out.println(e);
		}

		return con;
	}
}
// - See more at:
// http://www.codemiles.com/jsp-examples/login-using-jsp-servlets-and-database-following-mvc-t10898.html#sthash.erjdhXpE.dpuf