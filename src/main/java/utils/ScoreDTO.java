/**
 * @file ScoreDTO.java
 * @brief Data Transfer Object (DTO) class representing a player's score in the game.
 *
 * This class encapsulates the score-related data for a player, including the number of combat wins,
 * city wins, and the total score. It also includes methods to get and set these attributes.
 */
package utils;

public class ScoreDTO {
    private int id;
    private int idGame;
    private int idUser;
    private int idPlayer;
    private int nCombatWin;
    private int nCityWin;
    private int score;

    /**
     * @brief Default constructor for the ScoreDTO class.
     */
    public ScoreDTO() {
    }

    /**
     * @brief Parameterized constructor for the ScoreDTO class.
     * @param id The ID of the score.
     * @param idUser The user ID.
     * @param idPlayer The player ID.
     * @param nCombatWin The number of combat wins.
     * @param nCityWin The number of city wins.
     * @param score The total score.
     */
    public ScoreDTO(int id, int idUser, int idPlayer, int nCombatWin, int nCityWin, int score) {
        this.id = id;
        this.idUser = idUser;
        this.idPlayer = idPlayer;
        this.nCombatWin = nCombatWin;
        this.nCityWin = nCityWin;
        this.score = score;
    }

    /**
     * @brief Gets the ID of the score.
     * @return The ID of the score.
     */
    public int getId() {
        return id;
    }

    /**
     * @brief Sets the ID of the score.
     * @param id The ID of the score.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @brief Gets the user ID.
     * @return The user ID.
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * @brief Sets the user ID.
     * @param idUser The user ID.
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * @brief Gets the player ID.
     * @return The player ID.
     */
    public int getIdPlayer() {
        return idPlayer;
    }

    /**
     * @brief Sets the player ID.
     * @param idPlayer The player ID.
     */
    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    /**
     * @brief Gets the number of combat wins.
     * @return The number of combat wins.
     */
    public int getNCombatWin() {
        return nCombatWin;
    }

    /**
     * @brief Sets the number of combat wins.
     * @param nCombatWin The number of combat wins.
     */
    public void setNCombatWin(int nCombatWin) {
        this.nCombatWin = nCombatWin;
    }

    /**
     * @brief Gets the number of city wins.
     * @return The number of city wins.
     */
    public int getNCityWin() {
        return nCityWin;
    }

    /**
     * @brief Sets the number of city wins.
     * @param nCityWin The number of city wins.
     */
    public void setNCityWin(int nCityWin) {
        this.nCityWin = nCityWin;
    }

    /**
     * @brief Gets the total score.
     * @return The total score.
     */
    public int getScore() {
        return score;
    }

    /**
     * @brief Sets the total score.
     * @param score The total score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @brief Gets the game ID.
     * @return The game ID.
     */
    public int getIdGame() {
        return idGame;
    }

    /**
     * @brief Sets the game ID.
     * @param idGame The game ID.
     */
    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    /**
     * @brief Returns a string representation of the ScoreDTO object.
     * @return A string representation of the ScoreDTO object.
     */
    @Override
    public String toString() {
        return "ScoreDTO{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idPlayer=" + idPlayer +
                ", nCombatWin=" + nCombatWin +
                ", nCityWin=" + nCityWin +
                ", score=" + score +
                '}';
    }
}
