package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseDAOImpl{
	
	
	public static Integer authenticateUser(String username, String mdp) {
	    int userId = -1;  // Valeur par défaut si l'utilisateur n'est pas trouvé
	    try {
	        Connection connection = DbConnection.getConnection();
	        String query = "SELECT id FROM users WHERE username = ? AND mdp = ?";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setString(1, username);
	        stmt.setString(2, mdp); 

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            userId = rs.getInt("id"); 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return userId; 
	}

	 public static boolean createUser(String username, String mdp) {
	        boolean isCreated = false;
	        try {
	            Connection connection = DbConnection.getConnection();
	            String query = "INSERT INTO users (username, mdp) VALUES (?, ?)";
	            PreparedStatement stmt = connection.prepareStatement(query);
	            stmt.setString(1, username);
	            stmt.setString(2, mdp);

	            int rowsAffected = stmt.executeUpdate();
	            if (rowsAffected > 0) {
	                isCreated = true;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return isCreated;
	    }
	
	 public static String getUserName(int idUser) {
		 String name = "";
		    try {
		        Connection connection = DbConnection.getConnection();
		        String query = "SELECT username FROM users WHERE id = ?";
		        PreparedStatement stmt = connection.prepareStatement(query);
		        stmt.setLong(1, idUser);

		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            name = rs.getString("username"); 
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return name; 
	 }
	
}