package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.DateFormatter;

public class Test {

	public static void main(String[] args) throws ParseException {
		
		String date_s = "2011-01-18";

        // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"  
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dt.parse(date_s);

        // *** same for the format String below
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dt1.format(date));
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01").toString());
		// TODO Auto-generated method stub
//		Connection currentCon = ConnectionManager.getConnection();
		// Statement stmt = null;

//		String username = bean.getUsername();
//		String password = bean.getPassword();
		
/*		
		ResultSet rs = null;
		try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:socialmedia";
            Connection conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
            	
            	System.out.println("Connected to the database");
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                
                //dm.getTables(catalog, schemaPattern, tableNamePattern, types)
//                String[] types = {"TABLE"};
//                ResultSet sadasd = dm.getTables(null, null, "%", types);
//                while (sadasd.next()) {
//                  System.out.println(sadasd.getString(3));
//                }
                
                String sql = "insert into Liker (liker_id, person_id) "+
                			" values("+ String.valueOf(199) +","+2+") ;";

                PreparedStatement ps = conn.prepareStatement(sql,Statement.KEEP_CURRENT_RESULT);

//                int ss = ps.executeUpdate();
//                System.out.println("Inserted record's ID: --- " + ss);
//                if(ss > 0 )
//                {
//                	
//                }
                ResultSet asd = ps.getGeneratedKeys();
                int generatedKey = 0;
                if (asd.next()) {
                	
                    generatedKey = asd.getInt(1);
                    System.out.println("Inserted record's ID: --- " + generatedKey );
                   
                }

                
         
                
//                String searchQuery = "select * from Person";
//            	stmt = conn.createStatement();
//    			rs = stmt.executeQuery(searchQuery);
//    			while (rs.next()) {
//		            String username = rs.getString("name");
//		            System.out.println("Welcome " + username);
//				}
//    			boolean more = rs.next();
//    			// if user does not exist set the isValid variable to false
//    			if (!more) {
//    				System.out.println("Sorry, you are not a registered user! Please sign up first");
//    				
//    			}
//
//    			// if user exists set the isValid variable to true
//    			else if (more) {
//    				
//    				
//    				
//    			}
    			
                
                conn.close();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		
		// some exception handling
				finally {
					if (rs != null) {
						try {
							rs.close();
						} catch (Exception e) {
						}
						rs = null;
					}

					if (stmt != null) {
						try {
							stmt.close();
						} catch (Exception e) {
						}
						stmt = null;
					}

//					if (currentCon != null) {
//						try {
//							currentCon.close();
//						} catch (Exception e) {
//						}
//
//						currentCon = null;
//					}
				}
				*/
		
	}

}
