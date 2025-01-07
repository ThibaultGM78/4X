package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.ScoreDTO;
import utils.Constantes;

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
	
	 public static int createNewGame(int idUser) {
		int idGame = -1;
        try {
            Connection connection = DbConnection.getConnection();
            
            String query = "SELECT MAX(idGame) AS idGame FROM scores";
            PreparedStatement stmt = connection.prepareStatement(query);

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            idGame = (int) rs.getLong("idGame") + 1; 
	        }
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGame;
	 }
	 
	 public static void addPlayer(int idUser, int idPlayer, int idGame) {
        try {
        	 Connection connection = DbConnection.getConnection();
 	         String query = "INSERT INTO scores (idGame, idUser, idPlayer, nCombatWin, nCityWin, score) VALUES (?, ?, ?, 0, 0, 0)";
 	         PreparedStatement stmt = connection.prepareStatement(query);
	         stmt.setLong(1, idGame);
	         stmt.setLong(2, idUser);
	         stmt.setLong(3, idPlayer);
	         stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	 }
	 
	 public static void updateScore(int idGame, int idPlayer) {
		 int nCombatWin = 0;
		 int nCityWin = 0;
        try {
        	Connection connection = DbConnection.getConnection();
	        String query = "SELECT nCombatWin, nCityWin FROM scores WHERE idGame = ? AND idPlayer = ?";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setLong(1, idGame);
	        stmt.setLong(2, idPlayer); 

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            nCombatWin = rs.getInt("nCombatWin"); 
	            nCityWin = rs.getInt("nCityWin"); 
	        }
            
	        int score = nCombatWin * Constantes.SCORE_VALUE_COMBAT_WIN + nCityWin * Constantes.SCORE_VALUE_CITY_WIN;
	        
	        query = "UPDATE scores SET score = ? WHERE idGame = ? AND idPlayer = ?";
	        stmt = connection.prepareStatement(query);
            stmt.setLong(1, score);
            stmt.setLong(2, idGame);
            stmt.setLong(3, idPlayer);
            stmt.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
	 }
	 
	 public static void combatWin(int idGame, int idPlayer) {
          try {
        	  
        	  Connection connection = DbConnection.getConnection();
              String query = "UPDATE scores SET nCombatWin = nCombatWin + 1 WHERE idGame = ? AND idPlayer = ?";
              PreparedStatement stmt = connection.prepareStatement(query);
              stmt.setLong(1, idGame);
              stmt.setLong(2, idPlayer);
              stmt.executeUpdate();
              
              updateScore(idGame,idPlayer);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	 }
	 
	 public static void cityWin(int idGame, int idPlayer) {
         try {
       	  
       	  Connection connection = DbConnection.getConnection();
             String query = "UPDATE scores SET nCityWin = nCityWin + 1 WHERE idGame = ? AND idPlayer = ?";
             PreparedStatement stmt = connection.prepareStatement(query);
             stmt.setLong(1, idGame);
             stmt.setLong(2, idPlayer);
             stmt.executeUpdate();
             
             updateScore(idGame,idPlayer);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	 }
	 
	 public static List<ScoreDTO> getScoreGame(int idGame){
		 List<ScoreDTO> scores = new ArrayList<>();
		    try {
		        Connection connection = DbConnection.getConnection();
		        String query = "SELECT idPlayer,nCombatWin,nCityWin,score FROM scores WHERE idGame = ? ORDER BY score DESC";
		        PreparedStatement stmt = connection.prepareStatement(query);
		        stmt.setLong(1, idGame);

		        ResultSet rs = stmt.executeQuery();

		        while (rs.next()) {
	                ScoreDTO score = new ScoreDTO();
	                score.setIdPlayer(rs.getInt("idPlayer"));
	                score.setNCombatWin(rs.getInt("nCombatWin"));
	                score.setNCityWin(rs.getInt("nCityWin"));
	                score.setScore(rs.getInt("score"));

	                scores.add(score);
	            }
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return scores;
	 }
	 
	 public static List<ScoreDTO> getScoreHistory(int idPlayer){
		 List<ScoreDTO> scores = new ArrayList<>();
		    try {
		        Connection connection = DbConnection.getConnection();
		        String query = "SELECT idGame,nCombatWin,nCityWin,score FROM scores WHERE idPlayer = ? ORDER BY id";
		        PreparedStatement stmt = connection.prepareStatement(query);
		        stmt.setLong(1, idPlayer);

		        ResultSet rs = stmt.executeQuery();

		        while (rs.next()) {
	                ScoreDTO score = new ScoreDTO();
	                score.setIdGame(rs.getInt("idGame"));
	                score.setNCombatWin(rs.getInt("nCombatWin"));
	                score.setNCityWin(rs.getInt("nCityWin"));
	                score.setScore(rs.getInt("score"));

	                scores.add(score);
	            }
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return scores;
	 }
}