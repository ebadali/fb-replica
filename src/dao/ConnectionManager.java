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
            path = path+"db/socialmedia";
            String newpath = "D:/J2EE/Facebook-Replica/WebContent/socialmedia";
            String dbURL = "jdbc:sqlite:"+path;
			try {
				
//				con =DriverManager.getConnection(ConnectionManager.class.getClassLoader().getResource(dbURL).toString());
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

	public static void CloseConnection()
	{
		
	}
}
// - See more at:
// http://www.codemiles.com/jsp-examples/login-using-jsp-servlets-and-database-following-mvc-t10898.html#sthash.erjdhXpE.dpuf