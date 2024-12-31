package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/4x"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    public static Connection getConnection() throws SQLException {
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Connected to DB");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    	
    	return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void test() throws SQLException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
    	System.out.println("Coonection created");
    	
    }
}