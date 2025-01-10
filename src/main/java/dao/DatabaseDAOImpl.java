/**
 * @file DatabaseDAOImpl.java
 * @brief Data Access Object (DAO) implementation for interacting with the database.
 *
 * This class provides methods to authenticate users, create users, retrieve user information,
 * manage game scores, and update game-related data in the database.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.ScoreDTO;
import utils.Constantes;

public class DatabaseDAOImpl {

    /**
     * @brief Authenticates a user based on the provided username and password.
     * @param username The username of the user.
     * @param mdp The password of the user.
     * @return The user ID if authentication is successful, -1 otherwise.
     */
    public static Integer authenticateUser(String username, String mdp) {
        int userId = -1;  // Default value if the user is not found
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

    /**
     * @brief Creates a new user with the provided username and password.
     * @param username The username of the new user.
     * @param mdp The password of the new user.
     * @return True if the user is created successfully, false otherwise.
     */
    public static boolean createUser(String username, String mdp) {
        boolean isCreated = false;
        try {
            Connection connection = DbConnection.getConnection();

            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Le nom d'utilisateur existe déjà.");
                return false;
            }

            String insertQuery = "INSERT INTO users (username, mdp) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setString(2, mdp);

            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected > 0) {
                isCreated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCreated;
    }


    /**
     * @brief Retrieves the username of a user based on the user ID.
     * @param idUser The user ID.
     * @return The username of the user.
     */
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

    /**
     * @brief Creates a new game and returns the game ID.
     * @param idUser The user ID of the game creator.
     * @return The new game ID.
     */
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

    /**
     * @brief Adds a player to a game.
     * @param idUser The user ID of the player.
     * @param idPlayer The player ID.
     * @param idGame The game ID.
     */
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

    /**
     * @brief Updates the score of a player in a game.
     * @param idGame The game ID.
     * @param idPlayer The player ID.
     */
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

    /**
     * @brief Updates the combat win count for a player in a game.
     * @param idGame The game ID.
     * @param idPlayer The player ID.
     */
    public static void combatWin(int idGame, int idPlayer) {
        try {
            Connection connection = DbConnection.getConnection();
            String query = "UPDATE scores SET nCombatWin = nCombatWin + 1 WHERE idGame = ? AND idPlayer = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, idGame);
            stmt.setLong(2, idPlayer);
            stmt.executeUpdate();

            updateScore(idGame, idPlayer);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Updates the city win count for a player in a game.
     * @param idGame The game ID.
     * @param idPlayer The player ID.
     */
    public static void cityWin(int idGame, int idPlayer) {
        try {
            Connection connection = DbConnection.getConnection();
            String query = "UPDATE scores SET nCityWin = nCityWin + 1 WHERE idGame = ? AND idPlayer = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, idGame);
            stmt.setLong(2, idPlayer);
            stmt.executeUpdate();

            updateScore(idGame, idPlayer);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Retrieves the scores of all players in a game.
     * @param idGame The game ID.
     * @return A list of ScoreDTO objects representing the scores of all players in the game.
     */
    public static List<ScoreDTO> getScoreGame(int idGame) {
        List<ScoreDTO> scores = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            String query = "SELECT idPlayer, nCombatWin, nCityWin, score FROM scores WHERE idGame = ? ORDER BY score DESC";
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

    /**
     * @brief Retrieves the score history of a player.
     * @param idPlayer The player ID.
     * @return A list of ScoreDTO objects representing the score history of the player.
     */
    public static List<ScoreDTO> getScoreHistory(int idPlayer) {
        List<ScoreDTO> scores = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            String query = "SELECT idGame, nCombatWin, nCityWin, score FROM scores WHERE idPlayer = ? ORDER BY id";
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
