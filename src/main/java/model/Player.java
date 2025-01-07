/**
 * @file Player.java
 * @brief Class representing a player in the game.
 *
 * This class manages the player's attributes such as ID, name, user ID, gold, and score.
 * It also includes methods to modify these attributes and handle game events like winning a city or combat.
 */
package model;

import utils.Constantes;

public class Player {
    private int id;
    private String name;
    private int idUser;
    private int gold;
    private int score;

    /**
     * @brief Constructor for the Player class.
     * @param id The player's ID.
     * @param name The player's name.
     */
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.gold = 0;
        this.score = 0;
    }

    /**
     * @brief Default constructor for the Player class.
     */
    public Player() {
    }

    /**
     * @brief Gets the player's name.
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Sets the player's name.
     * @param name The player's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @brief Gets the player's ID.
     * @return The player's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @brief Sets the player's ID.
     * @param id The player's ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @brief Gets the player's user ID.
     * @return The player's user ID.
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * @brief Sets the player's user ID.
     * @param idUser The player's user ID.
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * @brief Gets the player's gold.
     * @return The player's gold.
     */
    public int getGold() {
        return gold;
    }

    /**
     * @brief Sets the player's gold.
     * @param gold The player's gold.
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * @brief Gets the player's score.
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * @brief Sets the player's score.
     * @param score The player's score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @brief Adds gold to the player's total.
     * @param g The amount of gold to add.
     */
    public void addGold(int g) {
        this.gold += g;
    }

    /**
     * @brief Subtracts gold from the player's total.
     * @param g The amount of gold to subtract.
     */
    public void retireGold(int g) {
        this.gold -= g;
    }

    /**
     * @brief Increases the player's score for winning a city.
     */
    public void cityWin() {
        this.score += Constantes.SCORE_VALUE_CITY_WIN;
    }

    /**
     * @brief Increases the player's score for winning a combat.
     */
    public void soldierWin() {
        this.score += Constantes.SCORE_VALUE_COMBAT_WIN;
    }
}
